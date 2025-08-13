package com.cool;
public class MappingAlgorithm {

    private static final long ENCRYPTION_KEY = 123456789L; // 任意密钥

    // 将 ID 转换为混淆的数字
    public static long encrypt(long id) {
        return id ^ ENCRYPTION_KEY; // 使用异或操作进行混淆
    }

    // 将混淆的数字恢复为原始的 ID
    public static long decrypt(long encryptedId) {
        return encryptedId ^ ENCRYPTION_KEY; // 逆操作恢复原始 ID
    }

    public static void main(String[] args) {
        long id = 2;
        long encrypted = encrypt(id);
        long decrypted = decrypt(encrypted);

        System.out.println("Original ID: " + id);
        System.out.println("Encrypted ID: " + encrypted);
        System.out.println("Decrypted ID: " + decrypted);
    }
}
