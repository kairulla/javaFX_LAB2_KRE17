package lab2_kre17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/electric_stove?serverTimezone=Asia/Almaty&useSSL=false";

            Properties authorization = new Properties();
            authorization.put("user", "root");
            authorization.put("password", "root");

            Scanner sc = new Scanner(System.in);
            m1:
            {
                while (true) {

                    String s1, s2, s3;
                    int i;
                    System.out.println("Что вы хотите выбрать?");
                    System.out.println("1 - добавить запись");
                    System.out.println("2 - удалить запись");
                    System.out.println("3 - изменить запись");
                    System.out.println("4 - фильтрация данных");
                    System.out.println("0 - выход");
                    try {
                        i = Integer.valueOf(sc.nextLine());
                    } catch (Exception e) {
                        System.out.println("До свидания");
                        i = 0;
                    }
                    switch (i) {
                        case 4:
                            Case4.run(url, authorization);
                            continue;
                        case 3:
                            Case3.run(url, authorization);
                            continue;
                        case 2:
                            Connection connection1 = DriverManager.getConnection(url, authorization);
                            // Создание оператора доступа к базе данных
                            Statement statement1 = connection1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);

//                            statement1.execute(String.format("INSERT INTO `electric_stove`.`es_model` "
//                                    + "(`model`, `price`, `color`) VALUES ('%s', %s, '%s')", s1, s2, s3));
                            // Выполнение запроса к базе данных, получение набора данных
                            ResultSet table1 = statement1.executeQuery("SELECT * FROM es_model");

                            table1.first(); // Выведем имена полей
                            for (int j = 1; j <= table1.getMetaData().getColumnCount(); j++) {
                                System.out.print(table1.getMetaData().getColumnName(j) + "\t\t");
                            }
                            System.out.println();

                            table1.beforeFirst(); // Выведем записи таблицы
                            while (table1.next()) {
                                for (int j = 1; j <= table1.getMetaData().getColumnCount(); j++) {
                                    System.out.print(table1.getString(j) + "\t\t");
                                }
                                System.out.println();
                            }

                            System.out.println("Какой ID удалить?");
                            String myId = sc.nextLine();
                            statement1.execute("DELETE FROM es_model WHERE id = " + Integer.valueOf(myId));

                            table1 = statement1.executeQuery("SELECT * FROM es_model");
                            while (table1.next()) {
                                for (int j = 1; j <= table1.getMetaData().getColumnCount(); j++) {
                                    System.out.print(table1.getString(j) + "\t\t");
                                }
                                System.out.println();
                                System.out.println();
                            }
                            System.out.println("Удалено запись с ID = " + myId);
                            System.out.println();

                            if (table1 != null) {
                                table1.close();
                            }
                            if (statement1 != null) {
                                statement1.close();
                            }
                            if (connection1 != null) {
                                connection1.close();
                            }
                            continue;

                        case 1:
                            Connection connection = DriverManager.getConnection(url, authorization);
                            // Создание оператора доступа к базе данных
                            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);
                            System.out.print("Модель плиты = ");
                            s1 = sc.nextLine();
                            System.out.print("Цена = ");
                            s2 = sc.nextLine();
                            System.out.print("Цвет = ");
                            s3 = sc.nextLine();
                            //            s1 = "!!!zzz123";
                            //            s2 = "555";
                            //            s3 = "!!!zzz789";
                            statement.execute(String.format("INSERT INTO `electric_stove`.`es_model` "
                                    + "(`model`, `price`, `color`) VALUES ('%s', %s, '%s')", s1, s2, s3));

                            // Выполнение запроса к базе данных, получение набора данных
                            ResultSet table = statement.executeQuery("SELECT * FROM es_model");

                            table.first(); // Выведем имена полей
                            for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                                System.out.print(table.getMetaData().getColumnName(j) + "\t\t");
                            }
                            System.out.println();

                            table.beforeFirst(); // Выведем записи таблицы
                            while (table.next()) {
                                for (int j = 1; j <= table.getMetaData().getColumnCount(); j++) {
                                    System.out.print(table.getString(j) + "\t\t");
                                }
                                System.out.println();
                            }

                            if (table != null) {
                                table.close();
                            }
                            if (statement != null) {
                                statement.close();
                            }
                            if (connection != null) {
                                connection.close();
                            }
                            continue;
                        case 0:
                            break m1;
                    }

                }
            }
        } catch (Exception e) {
            System.err.println("Error accessing database!");
            e.printStackTrace();
        }
    }

}
