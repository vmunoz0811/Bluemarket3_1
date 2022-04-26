package co.edu.unbosque.bluemarket3_1.dtos;

import com.opencsv.bean.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserService {

    public List<User1> getUsers() throws IOException {

        List<User1> users;

        try (InputStream is = UserService.class.getClassLoader()
                .getResourceAsStream("users.csv")) {

            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                CsvToBean<User1> csvToBean = new CsvToBeanBuilder<User>(br)
                        .withType(User1.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                users = csvToBean.parse();
            }
        }

        return users;
    }
    /*public void createUser(String username, String password, String path) throws IOException {
        String newLine = "\n" + username + "," + password + ",customer";
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        os.write(newLine.getBytes());
        os.close();
    }*/
    public void createUser(String username, String name, String lastname, String mail, String password, String Fcoins, String path) throws IOException {
        String newLine =  username + "," + name + ","+lastname+ "," + mail + "," + password +","+ Fcoins +"\n";
        FileOutputStream os = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        os.write(newLine.getBytes());
        os.close();
    }
}
