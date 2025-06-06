package com.TillDawn.Models.Enums;

import java.util.HashMap;
import java.util.Map;

public enum Language {
    ENGLISH("English", new HashMap<String, String>() {{
        // Menu Titles
        put("menu.login", "Login Menu");
        put("menu.signup", "Sign Up Menu");
        put("menu.settings", "Settings");
        put("menu.profile", "Profile Menu");
        put("menu.pregame", "Pre-game Menu");
        put("menu.leaderboard", "Leaderboard");
        
        // Buttons
        put("button.login", "Log in");
        put("button.signup", "Sign Up");
        put("button.back", "Back");
        put("button.settings", "Settings");
        put("button.profile", "Profile Menu");
        put("button.logout", "Log out");
        put("button.exit", "Exit");
        put("button.guest", "Enter as guest");
        put("button.forgot_password", "Forgot Password");
        put("button.change", "Change");
        put("button.delete_account", "Delete Account");
        
        // Labels
        put("label.username", "Username:");
        put("label.password", "Password:");
        put("label.remember_me", "Remember Me");
        put("label.security_question", "What is your favourite animal?");
        
        // Messages
        put("msg.username_empty", "Username can't be empty!");
        put("msg.username_exists", "Username exists! Choose another one!");
        put("msg.password_weak", "Password is weak!");
        put("msg.invalid_password", "Invalid password!");
        put("msg.user_not_found", "Such username doesn't exist!");
        
        // Settings
        put("settings.language", "Language");
        put("settings.controls", "Controls");
        put("settings.controls.default", "You can now move with w-a-s-d!");
        put("settings.controls.arrows", "You can now move with arrows!");
        put("settings.auto_reload", "Auto Reload");
        put("settings.toggle_auto_reload", "Toggle auto reload");
        put("settings.untoggle_auto_reload", "Untoggle auto reload");
        put("settings.gray_scale", "Toggle gray scale");
        put("settings.ungray_scale", "Untoggle gray scale");
        put("settings.mute_sfx", "Mute SFX");
        put("settings.unmute_sfx", "Unmute SFX");
        
        // Settings - Additional
        put("settings.volume", "Change volume:");
        put("settings.music", "Music");
        put("settings.reload", "reload");
        put("settings.auto_aim", "auto aim");
        put("settings.music.playing", "Music {0} is now playing!");
        put("settings.music.already_playing", "Music {0} is already playing!");
        
        // Control Messages
        put("settings.controls.reload", "You can now reload with {0}");
        put("settings.controls.auto_aim", "You can now auto aim with {0}!");
    }}),
    
    FRENCH("Français", new HashMap<String, String>() {{
        // Menu Titles
        put("menu.login", "Menu de Connexion");
        put("menu.signup", "Menu d'Inscription");
        put("menu.settings", "Paramètres");
        put("menu.profile", "Menu du Profil");
        put("menu.pregame", "Menu Pré-partie");
        put("menu.leaderboard", "Classement");
        
        // Buttons
        put("button.login", "Se connecter");
        put("button.signup", "S'inscrire");
        put("button.back", "Retour");
        put("button.settings", "Paramètres");
        put("button.profile", "Menu du Profil");
        put("button.logout", "Se déconnecter");
        put("button.exit", "Quitter");
        put("button.guest", "Entrer en tant qu'invité");
        put("button.forgot_password", "Mot de passe oublié");
        put("button.change", "Modifier");
        put("button.delete_account", "Supprimer le compte");
        
        // Labels
        put("label.username", "Nom d'utilisateur:");
        put("label.password", "Mot de passe:");
        put("label.remember_me", "Se souvenir de moi");
        put("label.security_question", "Quel est votre animal préféré?");
        
        // Messages
        put("msg.username_empty", "Le nom d'utilisateur ne peut pas être vide!");
        put("msg.username_exists", "Ce nom d'utilisateur existe déjà! Choisissez-en un autre!");
        put("msg.password_weak", "Le mot de passe est trop faible!");
        put("msg.invalid_password", "Mot de passe invalide!");
        put("msg.user_not_found", "Cet utilisateur n'existe pas!");
        
        // Settings
        put("settings.language", "Langue");
        put("settings.controls", "Contrôles");
        put("settings.controls.default", "Vous pouvez maintenant vous déplacer avec z-q-s-d!");
        put("settings.controls.arrows", "Vous pouvez maintenant vous déplacer avec les flèches!");
        put("settings.auto_reload", "Rechargement Auto");
        put("settings.toggle_auto_reload", "Activer le rechargement auto");
        put("settings.untoggle_auto_reload", "Désactiver le rechargement auto");
        put("settings.gray_scale", "Activer le mode gris");
        put("settings.ungray_scale", "Désactiver le mode gris");
        put("settings.mute_sfx", "Couper le son");
        put("settings.unmute_sfx", "Activer le son");
        
        // Settings - Additional
        put("settings.volume", "Changer le volume:");
        put("settings.music", "Musique");
        put("settings.reload", "recharger");
        put("settings.auto_aim", "visée automatique");
        put("settings.music.playing", "La musique {0} est maintenant en lecture!");
        put("settings.music.already_playing", "La musique {0} est déjà en lecture!");
        
        // Control Messages
        put("settings.controls.reload", "Vous pouvez maintenant recharger avec {0}");
        put("settings.controls.auto_aim", "Vous pouvez maintenant viser automatiquement avec {0}!");
    }});

    private final String displayName;
    private final Map<String, String> translations;

    Language(String displayName, Map<String, String> translations) {
        this.displayName = displayName;
        this.translations = translations;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String get(String key) {
        return translations.getOrDefault(key, key);
    }

    public static Language fromDisplayName(String displayName) {
        for (Language lang : values()) {
            if (lang.getDisplayName().equals(displayName)) {
                return lang;
            }
        }
        return ENGLISH; // Default to English if not found
    }
} 