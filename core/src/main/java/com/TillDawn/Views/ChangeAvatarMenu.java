package com.TillDawn.Views;

import com.TillDawn.Controllers.ControllersManager;
import com.TillDawn.Models.App;
import com.TillDawn.Models.DesktopFileChooser;
import com.TillDawn.Models.Enums.Paths;
import com.TillDawn.Models.GameAssetManager;
import com.TillDawn.Models.User;
import com.TillDawn.TillDawn;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ChangeAvatarMenu implements Screen {
    private TillDawn tillDawn = TillDawn.getTillDawn();
    private Stage stage;
    private final Skin skin = GameAssetManager.getManager().getSkin();
    private final User user;
    private Image avatar;
    private SelectBox<String> selectAvatar = new SelectBox<>(skin);

    private final Image image;

    public ChangeAvatarMenu() {
        user = App.getApp().getLoggedInUser();
        avatar = new Image();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        image = GameAssetManager.getManager().getOthersBackGround();
        UI();
    }

    private void UI(){
        Table table = new Table(skin);
        table.addActor(image);
        image.setPosition(0, 0);
        image.setSize((float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        table.setFillParent(true);
        stage.addActor(table);

        // Avatar display
        Texture profileTexture;
        if (user.getAvatarPath().startsWith("profiles/")) {
            // Use internal for default avatars
            profileTexture = new Texture(Gdx.files.internal(user.getAvatarPath()));
        } else {
            // Use local for custom avatars
            profileTexture = new Texture(Gdx.files.local(user.getAvatarPath()));
        }
        avatar.setDrawable(new TextureRegionDrawable(new TextureRegion(profileTexture)));
        avatar.setScaling(Scaling.fit);
        table.add(avatar).size(150, 150).padRight(50);

        // Create vertical table for controls
        Table controlsTable = new Table(skin);

        // Default avatar selection
        selectAvatar.setItems("1", "2", "3", "4");
        selectAvatar.setSelected("1");
        controlsTable.add(selectAvatar).padBottom(10).row();

        // Upload button
        TextButton uploadButton = new TextButton("Upload Custom Avatar", skin);
        controlsTable.add(uploadButton).padBottom(10).row();

        // Back button
        TextButton backButton = new TextButton("Back", skin);
        controlsTable.add(backButton);

        table.add(controlsTable);

        // Avatar selection listener
        selectAvatar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String path = Paths.AVATAR.getPath() + selectAvatar.getSelected() + ".png";
                user.setAvatarPath(path);
                // Save the change to JSON
                App.getApp().setLoggedInUser(user);
                updateAvatarDisplay(new Texture(Gdx.files.internal(path)));
            }
        });

        // Upload button listener
        uploadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Upload button clicked");
                try {
                    File selectedFile = DesktopFileChooser.openFileExplorer();
                    System.out.println("File chooser returned: " + selectedFile);

                    if (selectedFile != null && isImageFile(selectedFile.getName())) {
                        try {
                            // Create custom avatars directory in the local storage
                            String customAvatarsDir = "assets/custom_avatars";
                            FileHandle dirHandle = Gdx.files.local(customAvatarsDir);
                            dirHandle.mkdirs();

                            // Generate unique filename
                            String fileName = "custom_" + System.currentTimeMillis() + ".png";
                            String localPath = customAvatarsDir + "/" + fileName;

                            // Copy the file using LibGDX's file handling
                            FileHandle sourceFile = Gdx.files.absolute(selectedFile.getAbsolutePath());
                            FileHandle targetFile = Gdx.files.local(localPath);
                            sourceFile.copyTo(targetFile);
                            System.out.println("File copied successfully to: " + targetFile.path());

                            // Update user's avatar with the local path
                            user.setAvatarPath(localPath);
                            // Save the change to JSON
                            App.getApp().setLoggedInUser(user);
                            System.out.println("User avatar path updated: " + localPath);

                            // Update display using local path
                            Texture newTexture = new Texture(targetFile);
                            updateAvatarDisplay(newTexture);
                            System.out.println("Display updated successfully");
                        } catch (Exception e) {
                            System.err.println("Error uploading avatar: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("No file selected or invalid file type");
                    }
                } catch (Exception e) {
                    System.err.println("Error in file chooser: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        // Back button listener
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Going back to main menu...");
                System.out.println("Current user before transition: " +
                        (user != null ? "Username: " + user.getUsername() + ", Avatar: " + user.getAvatarPath() : "null"));

                // Make sure App still has the current user
                System.out.println("App's logged in user: " +
                        (App.getApp().getLoggedInUser() != null ?
                                "Username: " + App.getApp().getLoggedInUser().getUsername() +
                                        ", Avatar: " + App.getApp().getLoggedInUser().getAvatarPath()
                                : "null"));

                tillDawn.getScreen().dispose();
                tillDawn.setScreen(new ProfileMenu());
            }
        });
    }

    public void handleFileDrops(String[] files) {
        if (files != null && files.length > 0) {
            String filePath = files[0];
            if (isImageFile(filePath)) {
                Gdx.app.postRunnable(() -> {
                    try {
                        // Create custom avatars directory in the local storage
                        String customAvatarsDir = "assets/custom_avatars";
                        FileHandle dirHandle = Gdx.files.local(customAvatarsDir);
                        dirHandle.mkdirs();
                        
                        // Generate unique filename
                        String fileName = "custom_" + System.currentTimeMillis() + ".png";
                        String localPath = customAvatarsDir + "/" + fileName;
                        
                        // Copy the file using LibGDX's file handling
                        FileHandle sourceFile = Gdx.files.absolute(filePath);
                        FileHandle targetFile = Gdx.files.local(localPath);
                        sourceFile.copyTo(targetFile);
                        System.out.println("File copied successfully to: " + targetFile.path());
                        
                        // Update user's avatar with the local path
                        user.setAvatarPath(localPath);
                        // Save the change to JSON
                        App.getApp().setLoggedInUser(user);
                        System.out.println("User avatar path updated: " + localPath);
                        
                        // Update display using local path
                        Texture newTexture = new Texture(targetFile);
                        updateAvatarDisplay(newTexture);
                        System.out.println("Display updated successfully");
                    } catch (Exception e) {
                        System.err.println("Error handling dropped file: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            } else {
                System.err.println("Dropped file is not an image: " + filePath);
            }
        }
    }

    private void updateAvatarDisplay(Texture texture) {
        avatar.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    private boolean isImageFile(String fileName) {
        fileName = fileName.toLowerCase();
        return fileName.endsWith(".png") || fileName.endsWith(".jpg") ||
                fileName.endsWith(".jpeg") || fileName.endsWith(".gif");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}