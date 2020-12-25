package Game;

import Client.RoomUser;
import Client.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"ResultOfMethodCallIgnored", "DuplicatedCode"})
public class Transfer {
    public static Map<String, String> transferGameMap(String str) {
        String regex = "\\{[^]]*}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("[{}]", "").split(",");
        Map<String, String> gameMap = new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|", ","));
        }
        return gameMap;
    }

    public static RoomUser transferRoomUser(String users) {
        RoomUser roomUser = new RoomUser();
        String regex = "\\{[^]]*}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(users);
        matcher.find();
        String group = matcher.group();
        String[] split = group.substring(1, group.length() - 1).split("},");
        for (int i = 0; i < split.length - 1; i++) {
            split[i] += "}";
            matcher = compile.matcher(split[i]);
            matcher.find();
            String[] user = matcher.group().replaceAll("[{} ]", "").split(",");
            User userMessage = new User();
            Map<String, String> userMap = new HashMap<>();
            for (String s : user) {
                String[] user_split = s.split("=");
                userMap.put(user_split[0], user_split[1].replace("'", ""));
            }
            userMessage.setUID(userMap.get("UID"));
            userMessage.setPassword(userMap.get("password"));
            userMessage.setServerIP(userMap.get("serverIP"));
            userMessage.setServerPort(Integer.parseInt(userMap.get("serverPort")));
            if (i == 0) {
                roomUser.setUser_black(userMessage);
            } else {
                roomUser.setUser_write(userMessage);
            }
        }
        roomUser.setRoomID(Integer.parseInt(split[2].replace(" roomID=", "")));
        return roomUser;
    }


    public static Map<String, String> transferGameCommand(String str) {
        //"command-game:game={command=remake,roomID=id};"
        String regex = "\\{[^]]*}";//匹配中括号
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        matcher.find();
        String[] messageGame = matcher.group().replaceAll("[{}]", "").split(",");
        Map<String, String> gameMap = new HashMap<>();
        for (String s : messageGame) {
            String[] game_split = s.split("=");
            gameMap.put(game_split[0], game_split[1].replace("|", ","));
        }
        return gameMap;
    }
    public static String[] transferAttackDoubleUser(String message) {
        String regex = "\\[[^]]*]";//chat-正则匹配
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(message);//command:Client!attackUser:[(UID),(attackUserUID)];
        StringBuilder user_merge = new StringBuilder();
        while (matcher.find()) {
            user_merge.append(matcher.group().replaceAll("[()\\[\\]]", ""));
        }
        return user_merge.toString().split(",");//0 申请人 1被邀请人
    }
}
