package com.TillDawn.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import java.io.File;

public class DesktopFileChooser {
    public static File openFileExplorer() {
        System.out.println("Starting file chooser...");
        
        // Use LibGDX's native file handling
        FileHandle homeDir = Gdx.files.absolute(System.getProperty("user.home"));
        System.out.println("Home directory: " + homeDir.path());
        
        if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            String[] command = {
                "osascript",
                "-e", "tell application \"Finder\"",
                "-e", "activate",
                "-e", "set fileList to choose file with prompt \"Choose an image:\" of type {\"public.image\"}",
                "-e", "set filePath to POSIX path of fileList",
                "-e", "return filePath",
                "-e", "end tell"
            };

            try {
                System.out.println("Executing AppleScript command...");
                Process process = Runtime.getRuntime().exec(command);
                
                // Read the output
                java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
                String result = reader.readLine();
                System.out.println("AppleScript result: " + result);
                
                // Read any errors
                java.io.BufferedReader errorReader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getErrorStream()));
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    System.err.println("AppleScript error: " + errorLine);
                }
                
                // Wait for the process to complete
                int exitCode = process.waitFor();
                System.out.println("AppleScript exit code: " + exitCode);
                
                if (result != null && !result.trim().isEmpty()) {
                    return new File(result.trim());
                }
            } catch (Exception e) {
                System.err.println("Error opening file chooser:");
                e.printStackTrace();
            }
            return null;
        } else {
            return openSwingFileChooser();
        }
    }
    
    private static File openSwingFileChooser() {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".png") || name.endsWith(".jpg") || 
                           name.endsWith(".jpeg") || name.endsWith(".gif");
                }

                @Override
                public String getDescription() {
                    return "Image files (*.png, *.jpg, *.jpeg, *.gif)";
                }
            });
            
            int result = fileChooser.showOpenDialog(null);
            if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
