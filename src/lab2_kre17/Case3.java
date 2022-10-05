package lab2_kre17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Case3 {

    public static void run(String url, Properties authorization) throws SQLException {
        Connection connection2 = DriverManager.getConnection(url, authorization);
        // Создание оператора доступа к базе данных
        Statement statement2 = connection2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        // Выполнение запроса к базе данных, получение набора данных
        ResultSet table2 = statement2.executeQuery("SELECT * FROM es_model");

        table2.first(); // Выведем имена полей
        for (int j = 1; j <= table2.getMetaData().getColumnCount(); j++) {
            System.out.print(table2.getMetaData().getColumnName(j) + "\t\t");
        }
        System.out.println();

        table2.beforeFirst(); // Выведем записи таблицы
        while (table2.next()) {
            for (int j = 1; j <= table2.getMetaData().getColumnCount(); j++) {
                System.out.print(table2.getString(j) + "\t\t");
            }
            System.out.println();
        }

        System.out.println("Какой ID Изменить?");
        Scanner sc = new Scanner(System.in);
        String s1, s2, s3;
        String myId2 = sc.nextLine();

        System.out.print("Модель плиты = ");
        s1 = sc.nextLine(); // model price color
        System.out.print("Цена = ");
        s2 = sc.nextLine();
        System.out.print("Цвет = ");
        s3 = sc.nextLine();

        if (!myId2.equals("")) {
            statement2.executeUpdate("UPDATE es_model SET model = '" + s1 + "' WHERE id = " + myId2);
            statement2.executeUpdate("UPDATE es_model SET price = '" + s2 + "' WHERE id = " + myId2);
            statement2.executeUpdate("UPDATE es_model SET color = '" + s3 + "' WHERE id = " + myId2);
        }

        table2 = statement2.executeQuery("SELECT * FROM es_model");
        while (table2.next()) {
            for (int j = 1; j <= table2.getMetaData().getColumnCount(); j++) {
                System.out.print(table2.getString(j) + "\t\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.format("Запись с ID %s успешно изменена", myId2);
        System.out.println();

        if (table2 != null) {
            table2.close();
        }
        if (statement2 != null) {
            statement2.close();
        }
        if (connection2 != null) {
            connection2.close();
        }
    }
}
