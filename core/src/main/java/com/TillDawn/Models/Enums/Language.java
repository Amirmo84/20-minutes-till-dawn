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
        put("menu.pregame", "Pre-Game Menu");
        put("menu.leaderboard", "Leaderboard");
        put("menu.hint", "Hint Menu");
        
        // Buttons
        put("button.login", "Sign In");
        put("button.signup", "Sign Up");
        put("button.back", "Back");
        put("button.settings", "Settings");
        put("button.profile", "Profile Menu");
        put("button.logout", "Log Out");
        put("button.exit", "Exit");
        put("button.guest", "Enter as Guest");
        put("button.forgot_password", "Forgot Password");
        put("button.change", "Change");
        put("button.delete_account", "Delete Account");
        put("button.resume", "Resume Saved Game");
        put("button.play", "Play");
        
        // Labels
        put("label.username", "Username:");
        put("label.password", "Password:");
        put("label.remember_me", "Remember Me");
        put("label.security_question", "What is your favourite animal?");
        put("label.score", "Score:");
        
        // Messages
        put("msg.username_empty", "Username can't be empty!");
        put("msg.username_exists", "Username exists! Choose another one!");
        put("msg.password_weak", "Password is weak!");
        put("msg.invalid_password", "Invalid password!");
        put("msg.user_not_found", "Such username doesn't exist!");
        
        // Settings
        put("settings.language", "Language");
        put("settings.controls", "Change movement");
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
        
        // Leaderboard
        put("leaderboard.sort_by", "Sort by:");
        put("leaderboard.sort.score", "Score");
        put("leaderboard.sort.kills", "Kills");
        put("leaderboard.sort.time", "Time Alive");
        put("leaderboard.sort.username", "Username");
        put("leaderboard.rank", "Rank");
        put("leaderboard.username", "Username");
        put("leaderboard.score", "Score");
        put("leaderboard.kills", "Kills");
        put("leaderboard.time_alive", "Max Time Alive");
        
        // Pre-game menu
        put("pregame.choose_hero", "Choose your hero:");
        put("pregame.choose_weapon", "Choose your weapon:");
        put("pregame.choose_length", "Choose game length:");
        
        // Profile menu
        put("profile.change_username", "Change Username");
        put("profile.change_password", "Change Password");
        put("profile.delete_account", "Delete Account");
        put("profile.change_avatar", "Change Avatar");
        put("profile.enter_username", "Enter new username:");
        put("profile.enter_password", "Enter new password:");
        put("profile.error.empty_username", "Username can't be empty!");
        put("profile.error.username_taken", "Username is already taken!");
        put("profile.error.invalid_password", "Can't use this password!");
        put("profile.success.username_changed", "Username changed successfully!");
        put("profile.success.password_changed", "Password changed successfully!");

        // Hint Menu
        put("hint.select_hero", "SELECT HERO");
        put("hint.controls", "CONTROLS");
        put("hint.cheats", "CHEAT CODES");
        put("hint.abilities", "ABILITIES");
        put("hint.login_required", "Please log in to view controls");
        put("hint.hero_stats", "Name: %s      Hp: %d      Speed: %d");

        // Controls
        put("controls.up", "UP");
        put("controls.down", "DOWN");
        put("controls.left", "LEFT");
        put("controls.right", "RIGHT");
        put("controls.auto_aim", "AUTO AIM");
        put("controls.reload", "RELOAD");
        put("controls.shoot", "SHOOT");
        put("controls.left_click", "Left click");

        // Cheats
        put("cheats.key1", "Key 1");
        put("cheats.key2", "Key 2");
        put("cheats.key3", "Key 3");
        put("cheats.key4", "Key 4");
        put("cheats.key5", "Key 5");
        put("cheats.desc1", "Decrease time of the game by 1 minute");
        put("cheats.desc2", "Level up");
        put("cheats.desc3", "Increase HP");
        put("cheats.desc4", "Go to boss fight");
        put("cheats.desc5", "Increase ammo max");

        // Abilities
        put("abilities.vitality", "Vitality : HP max + 1");
        put("abilities.damager", "Damager : Increase damage of the gun by *1.5f (temporary)");
        put("abilities.procrease", "Procrease : Increase the projectile by 1");
        put("abilities.amocrease", "Amocrease : Increase the ammo max by 5");
        put("abilities.speedy.name", "Speedy");
        put("abilities.speedy.desc", "Increase the speed by *2 (temporary)");
    }}),
    
    FRENCH("Français", new HashMap<String, String>() {{
        // Menu Titles
        put("menu.login", "Menu de Connexion");
        put("menu.signup", "Menu d'Inscription");
        put("menu.settings", "Paramètres");
        put("menu.profile", "Menu du Profil");
        put("menu.pregame", "Menu Pré-Partie");
        put("menu.leaderboard", "Classement");
        put("menu.hint", "Menu d'Aide");
        
        // Buttons
        put("button.login", "Se Connecter");
        put("button.signup", "S'inscrire");
        put("button.back", "Retour");
        put("button.settings", "Paramètres");
        put("button.profile", "Menu du Profil");
        put("button.logout", "Se Déconnecter");
        put("button.exit", "Quitter");
        put("button.guest", "Entrer en tant qu'Invité");
        put("button.forgot_password", "Mot de Passe Oublié");
        put("button.change", "Modifier");
        put("button.delete_account", "Supprimer le Compte");
        put("button.resume", "Reprendre la Partie");
        put("button.play", "Jouer");
        
        // Labels
        put("label.username", "Nom d'utilisateur:");
        put("label.password", "Mot de passe:");
        put("label.remember_me", "Se souvenir de moi");
        put("label.security_question", "Quel est votre animal préféré?");
        put("label.score", "Score:");
        
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
        
        // Leaderboard
        put("leaderboard.sort_by", "Trier par:");
        put("leaderboard.sort.score", "Score");
        put("leaderboard.sort.kills", "Éliminations");
        put("leaderboard.sort.time", "Temps de Survie");
        put("leaderboard.sort.username", "Nom d'utilisateur");
        put("leaderboard.rank", "Rang");
        put("leaderboard.username", "Nom d'utilisateur");
        put("leaderboard.score", "Score");
        put("leaderboard.kills", "Éliminations");
        put("leaderboard.time_alive", "Temps Max de Survie");
        
        // Pre-game menu
        put("pregame.choose_hero", "Choisissez votre héros:");
        put("pregame.choose_weapon", "Choisissez votre arme:");
        put("pregame.choose_length", "Choisissez la durée de la partie:");
        
        // Profile menu
        put("profile.change_username", "Changer le nom d'utilisateur");
        put("profile.change_password", "Changer le mot de passe");
        put("profile.delete_account", "Supprimer le compte");
        put("profile.change_avatar", "Changer l'avatar");
        put("profile.enter_username", "Entrez le nouveau nom d'utilisateur:");
        put("profile.enter_password", "Entrez le nouveau mot de passe:");
        put("profile.error.empty_username", "Le nom d'utilisateur ne peut pas être vide!");
        put("profile.error.username_taken", "Ce nom d'utilisateur est déjà pris!");
        put("profile.error.invalid_password", "Ce mot de passe ne peut pas être utilisé!");
        put("profile.success.username_changed", "Nom d'utilisateur changé avec succès!");
        put("profile.success.password_changed", "Mot de passe changé avec succès!");

        // Hint Menu
        put("hint.select_hero", "SÉLECTIONNER UN HÉROS");
        put("hint.controls", "CONTRÔLES");
        put("hint.cheats", "CODES DE TRICHE");
        put("hint.abilities", "CAPACITÉS");
        put("hint.login_required", "Veuillez vous connecter pour voir les contrôles");
        put("hint.hero_stats", "Nom: %s      PV: %d      Vitesse: %d");

        // Controls
        put("controls.up", "HAUT");
        put("controls.down", "BAS");
        put("controls.left", "GAUCHE");
        put("controls.right", "DROITE");
        put("controls.auto_aim", "VISÉE AUTO");
        put("controls.reload", "RECHARGER");
        put("controls.shoot", "TIRER");
        put("controls.left_click", "Clic gauche");

        // Cheats
        put("cheats.key1", "Touche 1");
        put("cheats.key2", "Touche 2");
        put("cheats.key3", "Touche 3");
        put("cheats.key4", "Touche 4");
        put("cheats.key5", "Touche 5");
        put("cheats.desc1", "Diminuer le temps de jeu d'une minute");
        put("cheats.desc2", "Monter de niveau");
        put("cheats.desc3", "Augmenter les PV");
        put("cheats.desc4", "Aller au combat de boss");
        put("cheats.desc5", "Augmenter les munitions max");

        // Abilities
        put("abilities.vitality", "Vitalité : PV max + 1");
        put("abilities.damager", "Dégâts : Augmente les dégâts de l'arme de *1.5 (temporaire)");
        put("abilities.procrease", "Procrease : Augmente les projectiles de 1");
        put("abilities.amocrease", "Amocrease : Augmente les munitions max de 5");
        put("abilities.speedy.name", "Rapidité");
        put("abilities.speedy.desc", "Augmente la vitesse de *2 (temporaire)");
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