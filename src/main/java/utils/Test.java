package utils;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Arrays;
import java.util.Objects;

public class Test extends DBContext{
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16; // 16 bytes = 128 bits
    private static final int ITERATION_COUNT = 65536;
    private static final int KEY_LENGTH = 256; // 256 bits

    public void main(String[] args) throws Exception {

    }


}
