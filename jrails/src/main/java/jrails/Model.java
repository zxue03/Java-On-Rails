package jrails;

import java.util.*;
import java.io.*;
import java.lang.reflect.*;

public class Model {

	private static int next_id = 1;
	public int id = 0;
	private static Set<?> allowed_types = new HashSet<>(Arrays.asList(String.class, int.class, boolean.class));
	private static String dbs_path = "../dbs/";

	public void save() {
		/* this is an instance of the current model */
		Class<?> model = this.getClass();
		String model_name = model.getName();
		List<Field> columns = validate(this.getClass());
		List<Object> values = getVal(columns);
		setup();
		String db_path = dbs_path + model_name;
		try {
			if (id == 0) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(db_path + ".txt", true));
				this.id = next_id;
				writer.write(String.valueOf(this.id) + ",");
				for (int i = 0; i < values.size(); i++) {
					if (values.get(i) == null) {
						writer.write("null");
					} else {
						writer.write(values.get(i).toString());
					}
					if (i != values.size() - 1) {
						writer.write(",");
					} else {
						writer.write("\n");
					}
				}
				writer.flush();
				writer.close();
				writer = new BufferedWriter(new FileWriter(dbs_path + "next_id.txt"));
				next_id++;
				writer.write(String.valueOf(next_id));
				writer.flush();
				writer.close();
			} else {
				BufferedWriter writer = new BufferedWriter(new FileWriter(db_path + "_update.txt"));
				BufferedReader reader = new BufferedReader(new FileReader(db_path + ".txt"));
				String line = reader.readLine();
				while (line != null) {
					int curr_id = Integer.parseInt(line.split(",")[0]);
					if (curr_id == this.id) {
						writer.write(String.valueOf(this.id) + ",");
						for (int i = 0; i < values.size(); i++) {
							writer.write(values.get(i).toString());
							if (i != values.size() - 1) {
								writer.write(",");
							} else {
								writer.write("\n");
							}
						}
					} else {
						writer.write(line + "\n");
					}
					line = reader.readLine();
				}
				reader.close();
				writer.flush();
				writer.close();
				File old_db = new File(db_path + ".txt");
				File updated_db = new File(db_path + "_update.txt");
				updated_db.renameTo(old_db);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int id() {
		return this.id;
	}

	public static <T> T find(Class<T> c, int id) {

		List<Field> columns = validate(c);

		String model_name = c.getName();
		String db_path = dbs_path + model_name;
		File db = new File(db_path + ".txt");
		if (!db.exists()) {
			return null;
		}

		T found_model = null;
		boolean found = false;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(db));

			found_model = (T) c.getConstructor().newInstance();
			String line = reader.readLine();

			while (line != null) {
				String[] values = line.split(",");
				int curr_id = Integer.parseInt(values[0]);
				if (curr_id == id) {
					found = true;
					Field model_id = c.getField("id");
					model_id.set(found_model, id);
					for (int i = 1; i < values.length; i++) {
						if (values[i].equals("null")) {
							continue;
						}
						if (values[i].equals("true") || values[i].equals("false")) {
							System.out.println(values[i] + " " + Boolean.parseBoolean(values[i]));
							columns.get(i - 1).set(found_model, Boolean.parseBoolean(values[i]));

						} else {
							try {
								Integer integer = Integer.parseInt(values[i]);

								columns.get(i - 1).set(found_model, integer);
							} catch (NumberFormatException e) {
								columns.get(i - 1).set(found_model, values[i]);
							}
						}

					}
				}
				line = reader.readLine();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return found ? found_model : null;
	}

	public static <T> List<T> all(Class<T> c) {
		// Returns a List<element type>
		List<Field> columns = validate(c);

		String model_name = c.getName();
		String db_path = dbs_path + model_name;
		List<T> all_models = new ArrayList<>();
		File db = new File(db_path + ".txt");

		if (!db.exists()) {
			return all_models;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(db));

			String line = reader.readLine();

			while (line != null) {
				String[] values = line.split(",");
				T curr_model = (T) c.getConstructor().newInstance();
				int curr_id = Integer.parseInt(values[0]);
				Field model_id = c.getField("id");
				model_id.set(curr_model, curr_id);
				for (int i = 1; i < values.length; i++) {
					if (values[i].equals("null")) {
						continue;
					}
					if (values[i].equals("true") || values[i].equals("false")) {
						System.out.println(values[i] + " " + Boolean.parseBoolean(values[i]));
						columns.get(i - 1).set(curr_model, Boolean.parseBoolean(values[i]));

					} else {
						try {
							Integer integer = Integer.parseInt(values[i]);

							columns.get(i - 1).set(curr_model, integer);
						} catch (NumberFormatException e) {
							columns.get(i - 1).set(curr_model, values[i]);
						}
					}

				}
				all_models.add(curr_model);
				line = reader.readLine();
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return all_models;
	}

	public void destroy() {
		if (this.id == 0) {
			throw new UnsupportedOperationException();
		}

		Class<?> model = this.getClass();
		String model_name = model.getName();
		String db_path = dbs_path + model_name;

		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(db_path + "_update.txt"));
			BufferedReader reader = new BufferedReader(new FileReader(db_path + ".txt"));
			String line = reader.readLine();
			while (line != null) {
				int curr_id = Integer.parseInt(line.split(",")[0]);
				if (curr_id != this.id) {
					writer.write(line + "\n");
				}
				line = reader.readLine();
			}
			reader.close();
			writer.flush();
			writer.close();
			File old_db = new File(db_path + ".txt");
			File updated_db = new File(db_path + "_update.txt");
			updated_db.renameTo(old_db);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public static void reset() {
		File dbs = new File(dbs_path);
		if (!dbs.exists()) {
			return;
		}
		for (File db : dbs.listFiles()) {
			db.delete();
		}
		dbs.delete();

	}

	private void setup() {

		File db_dir = new File(dbs_path);
		if (!db_dir.exists()) {
			db_dir.mkdir();
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(dbs_path + "next_id.txt"));
				writer.write("1");
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(dbs_path + "next_id.txt"));
			next_id = Integer.parseInt(reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static List<Field> validate(Class<?> model) {
		Field[] fields = model.getDeclaredFields();
		List<Field> columns = new ArrayList<>();
		for (Field field : fields) {
			if (field.getAnnotation(Column.class) != null) {
				if (!allowed_types.contains(field.getType())) {
					throw new UnsupportedOperationException();
				}
				columns.add(field);
			}
		}
		return columns;
	}

	private List<Object> getVal(List<Field> columns) {
		List<Object> values = new ArrayList<>();
		for (Field column : columns) {
			try {
				Object value = column.get(this);
				values.add(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return values;
	}

}
