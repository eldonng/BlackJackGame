package main.storage;

import main.PlayerProfile;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class PlayerProfileStorage {

    static String filePath = "data/profile.xml";

    public static void saveToFile(PlayerProfile profile) {
        File file = new File(filePath);
         XmlSerializablePlayerProfile data = new XmlSerializablePlayerProfile(profile);

         try {
             JAXBContext context = JAXBContext.newInstance(data.getClass());
             Marshaller m = context.createMarshaller();
             m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

             m.marshal(data, file);
         } catch (JAXBException e) {
             e.printStackTrace();
         }

    }

    public static XmlSerializablePlayerProfile readFromFile() {
        File file = new File(filePath);
        try {
            JAXBContext context = JAXBContext.newInstance(XmlSerializablePlayerProfile.class);
            Unmarshaller um = context.createUnmarshaller();

            return ((XmlSerializablePlayerProfile) um.unmarshal(file));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return new XmlSerializablePlayerProfile();
    }

    public static String getFilePath() {
        return filePath;
    }

    public static boolean fileExists() {
        File file = new File(filePath);
        return file.exists();
    }

    public static void createFile() {
        File file = new File(filePath);
        try {
            File parentDir = file.getParentFile();

            if (parentDir != null) {
                if (!parentDir.exists() && !parentDir.mkdirs()) {
                    throw new IOException("Failed to make directories of " + parentDir.getName());
                }
            }

            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
