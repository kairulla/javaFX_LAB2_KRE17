package lab2_kre17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Case4 {

    public static void run (String url, Properties authorization) throws SQLException {
        Connection connection3 = DriverManager.getConnection(url, authorization);
        // Создание оператора доступа к базе данных
        Statement statement3 = connection3.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        // Выполнение запроса к базе данных, получение набора данных
        ResultSet table3 = statement3.executeQuery("SELECT * FROM es_model");

        table3.first(); // Выведем имена полей
        for (int j = 1; j <= table3.getMetaData().getColumnCount(); j++) {
            System.out.print(table3.getMetaData().getColumnName(j) + "\t\t");
        }
        System.out.println();

        table3.beforeFirst(); // Выведем записи таблицы
        while (table3.next()) {
            for (int j = 1; j <= table3.getMetaData().getColumnCount(); j++) {
                System.out.print(table3.getString(j) + "\t\t");
            }
            System.out.println();
        }

        System.out.print("Введите фрагмент названия для фильтрации: ");
        Scanner sc = new Scanner(System.in);
        String filter = sc.nextLine();

        table3 = statement3.executeQuery("SELECT * FROM es_model WHERE model like '%"
                + filter + "%' ORDER BY color DESC");

        //table3 = statement3.executeQuery("SELECT * FROM es_model");
        while (table3.next()) {
            for (int j = 1; j <= table3.getMetaData().getColumnCount(); j++) {
                System.out.print(table3.getString(j) + "\t\t");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Конец фильтрации");
        System.out.println();

        if (table3 != null) {
            table3.close();
        }
        if (statement3 != null) {
            statement3.close();
        }
        if (connection3 != null) {
            connection3.close();
        }
    }
}
