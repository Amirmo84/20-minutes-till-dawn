package com.TillDawn.Controllers;

import com.TillDawn.Controllers.MenuControllers.*;

public class ControllersManager {
    public static SignUpMenuController signUpMenuController = new SignUpMenuController();

    public static LoginMenuController loginMenuController = new LoginMenuController();

    public static MainMenuController mainMenuController = new MainMenuController();

    public static ForgotPasswordController forgotPasswordController = new ForgotPasswordController();

    public static ProfileMenuController profileMenuController = new ProfileMenuController();

    public static SettingsController settingsController = new SettingsController();

    public static PreGameController preGameController = new PreGameController();
}
