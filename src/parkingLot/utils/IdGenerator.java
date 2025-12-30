package parkingLot.utils;

import java.util.Random;

public class IdGenerator {

    public static String generateId(String prefix){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int len = chars.length();
        Random random = new Random();
        StringBuilder id = new StringBuilder();

        for(int i=0;i<6;i++){
            int index = random.nextInt(len);
            id.append(chars.charAt(index));
        }
        id.insert(0, prefix);
        return id.toString();
    }

}
