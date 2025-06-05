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

    public ChangeAvatarMenu() {
        user = App.getApp().getLoggedInUser();
        avatar = new Image();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        UI();
    }

    private void UI(){
        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);
        
        // Avatar display
        Texture profileTexture;
        if (user.getAvatarPath().startsWith("profiles/")) {
            // Default avatars are loaded from internal assets
            profileTexture = new Texture(Gdx.files.internal(user.getAvatarPath()));
        } else {
            // Custom avatars are loaded with absolute path
            profileTexture = new Texture(Gdx.files.absolute(user.getAvatarPath()));
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
                            // Create custom avatars directory in assets folder
                            String customAvatarsDir = "assets/custom_avatars/";
                            File dir = new File(customAvatarsDir);
                            boolean created = dir.mkdirs();
                            System.out.println("Directory created: " + created + " at " + dir.getAbsolutePath());
                            
                            // Generate unique filename
                            String fileName = "custom_" + System.currentTimeMillis() + ".png";
                            String targetPath = dir.getAbsolutePath() + File.separator + fileName;
                            System.out.println("Target path: " + targetPath);
                            
                            // Copy the file
                            File targetFile = new File(targetPath);
                            Files.copy(selectedFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            System.out.println("File copied successfully");
                            
                            // Update user's avatar with the absolute path
                            user.setAvatarPath(targetFile.getAbsolutePath());
                            System.out.println("User avatar path updated: " + targetFile.getAbsolutePath());
                            
                            // Update display using absolute path
                            Texture newTexture = new Texture(Gdx.files.absolute(targetFile.getAbsolutePath()));
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
                tillDawn.getScreen().dispose();
                tillDawn.setScreen(new MainMenu());
            }
        });
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
