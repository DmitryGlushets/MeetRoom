package ru.glushets.meetroom.avatar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

public class Avatar {

    private static final String BASE64_PREFIX = "data:image/png;base64,";

    private static final int WIDTH = 70;

    private static final int HEIGHT = 70;

    private static final int GRID = 5;

    public static String getAvatar(String username) {
        String avatar = null;
        int hashUsername = Math.abs(username.hashCode());

        try {
            avatar = createBase64Avatar(hashUsername);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return BASE64_PREFIX + avatar;
    }

    public static String createBase64Avatar(int hashUsername) throws IOException {
        return new String(Base64.getEncoder().encode(createByteArray(hashUsername)));
    }

    public static byte[] createByteArray(int hashUsername) throws IOException {
        int padding = WIDTH / 2;
        int size = WIDTH * GRID + WIDTH;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(new Color(240, 240, 240));
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(randomColor());

        char[] idChars = createIdent(hashUsername);
        int i = idChars.length;
        for (int x = 0; x < Math.ceil(GRID / 2.0); x++) {
            for (int y = 0; y < GRID; y++) {
                if (idChars[--i] < 53) {
                    graphics.fillRect((padding + x * WIDTH), (padding + y * WIDTH), WIDTH, HEIGHT);
                    if (x < Math.floor(GRID / 2)) {
                        graphics.fillRect((padding + ((GRID - 1) - x) * WIDTH), (padding + y * WIDTH), WIDTH, HEIGHT);
                    }
                }
            }
        }

        graphics.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static Color randomColor() {
        int fc = 80;
        int bc = 200;
        Random random = new Random();
        int r = fc + random.nextInt(Math.abs(bc - fc));
        int g = fc + random.nextInt(Math.abs(bc - fc));
        int b = fc + random.nextInt(Math.abs(bc - fc));
        return new Color(r, g, b);
    }

    private static char[] createIdent(int hashUsername) {
        BigInteger biContent = new BigInteger((hashUsername + "").getBytes());
        BigInteger bi = new BigInteger(hashUsername + "identicon" + hashUsername, 36);
        bi = bi.xor(biContent);
        return bi.toString(10).toCharArray();
    }
}
