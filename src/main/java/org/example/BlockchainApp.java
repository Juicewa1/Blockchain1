package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Define a Student class
class Student {
    private String name;
    private double gpa;
    private int credits;

    public Student(String name, double gpa, int credits) {
        this.name = name;
        this.gpa = gpa;
        this.credits = credits;
    }

    // Getters for student properties
    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }
}

// Define a CreditCardTransaction class
class CreditCardTransaction {
    private String cardNumber;
    private double amount;
    private Date timestamp;

    public CreditCardTransaction(String cardNumber, double amount) {
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.timestamp = new Date();
    }

    // Getters for transaction properties
    public String getCardNumber() {
        return cardNumber;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}

// Define a BankAccount class
class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // Getters for bank account properties
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}

// Define a Block class
class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private Object data;

    public Block(int index, String previousHash, Object data) {
        this.index = index;
        this.timestamp = new Date().getTime();
        this.previousHash = previousHash;
        this.data = data;
        this.hash = calculateHash();
    }


    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            int nonce = 0;
            String input;

            while (true) {
                input = index + timestamp + previousHash + data.toString() + nonce;
                byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hashBytes) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                String hash = hexString.toString();


                if (hash.startsWith("00")) {
                    return hash;
                }


                nonce++;
            }
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // Getters
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public Object getData() {
        return data;
    }
}

// Define a Blockchain class
class Blockchain {
    private List<Block> chain;

    // Constructor
    public Blockchain() {
        chain = new ArrayList<Block>();
        // Create the genesis block (the first block in the chain)
        chain.add(new Block(0, "0", new Student("Bill Nye", 2.0, 14)));
    }

    // Add a new block to the blockchain
    public void addBlock(Object data) {
        Block previousBlock = chain.get(chain.size() - 1);
        Block newBlock = new Block(previousBlock.getIndex() + 1, previousBlock.getHash(), data);
        chain.add(newBlock);
    }

    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            Object data = block.getData();

            if (data instanceof Student) {
                Student student = (Student) data;
                System.out.println("Student Name: " + student.getName());
                System.out.println("Student GPA: " + student.getGpa());
                System.out.println("Student Credits: " + student.getCredits());
            } else if (data instanceof CreditCardTransaction) {
                CreditCardTransaction transaction = (CreditCardTransaction) data;
                System.out.println("Card Number: " + transaction.getCardNumber());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Timestamp: " + transaction.getTimestamp());
            } else if (data instanceof BankAccount) {
                BankAccount account = (BankAccount) data;
                System.out.println("Account Number: " + account.getAccountNumber());
                System.out.println("Balance: " + account.getBalance());
            }
            System.out.println();
        }
    }
}

public class BlockchainApp {
    public static void main(String[] args) {
        Blockchain blockchain = new Blockchain();


        Student student1 = new Student("Jill Oakes", 4.0, 105);
        CreditCardTransaction transaction1 = new CreditCardTransaction("1234-5678-9012-3456", 100.0);
        BankAccount account1 = new BankAccount("1234567890", 1000.0);

        blockchain.addBlock(student1);
        blockchain.addBlock(transaction1);
        blockchain.addBlock(account1);

        blockchain.printBlockchain();
    }
}