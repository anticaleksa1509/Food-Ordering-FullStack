package raf.rs.WebProject2025.bootStrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.UserRepo;
import raf.rs.WebProject2025.securityAspect.HashPassword;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BootStrapClass  implements CommandLineRunner {

    private final UserRepo userRepo;
    private final HashPassword hashPassword;


    @Override
    public void run(String... args) throws Exception {

        String fileName = "C:\\Users\\alekaa\\korisnici.txt";//putanja?????
        Random random = new Random();
        User admin = new User();
        admin.setName("Admin");
        admin.setLastName("Admin");
        admin.setPassword(hashPassword.encode("admincar123"));
        admin.setEmail("admin@gmail.com");
        admin.setRole("Administrator");
        admin.setCan_read(true);
        admin.setCan_delete(true);
        admin.setCan_update(true);
        admin.setCan_create(true);
        admin.setCan_create_order(true);
        admin.setCan_search_order(true);
        admin.setCan_cancel_order(true);
        admin.setCan_track_order(true);
        admin.setCan_schedule_order(true);
        userRepo.save(admin);

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) != null){
                User user = new User();
                String[] param = line.split(",");
                user.setName(param[0]);
                user.setLastName(param[1]);
                user.setEmail(param[2]);
                user.setPassword(hashPassword.encode(param[3]));
                user.setCan_create(random.nextBoolean());
                user.setCan_delete(random.nextBoolean());
                user.setCan_update(random.nextBoolean());
                user.setCan_read(random.nextBoolean());
                user.setCan_create_order(random.nextBoolean());
                user.setCan_search_order(true);//all users all allowed to see their own orders
                user.setCan_track_order(random.nextBoolean());
                user.setCan_cancel_order(random.nextBoolean());
                user.setCan_schedule_order(random.nextBoolean());
                user.setRole("User");
                userRepo.save(user);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
