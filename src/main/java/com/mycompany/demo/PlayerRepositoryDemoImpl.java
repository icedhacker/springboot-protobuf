package com.mycompany.demo;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Arrays.asList;

@Service
public class PlayerRepositoryDemoImpl implements PlayerRepository {

    Map<Integer, PlayerProtos.Player> players = new ConcurrentHashMap<>();

    PlayerRepositoryDemoImpl() {
        // populate with some dummy data
        asList(player(1, "Mahendra Singh Dhoni", "Dhoni", PlayerProtos.SportType.CRICKET),
                player(2, "Virat Kohli", "Kohli", PlayerProtos.SportType.CRICKET),
                player(3, "Cristiano Ronaldo", "Ronaldo", PlayerProtos.SportType.SOCCER),
                player(4, "Lionel Messi", "Messi", PlayerProtos.SportType.SOCCER))
                .forEach(p -> players.put(p.getId(), p));
    }

    @Override
    public PlayerProtos.Player findById(int id) {
        return players.get(id);
    }

    @Override
    public PlayerProtos.PlayerList findAll() {
        return PlayerProtos.PlayerList.newBuilder()
                .addAllPlayers(players.values())
                .build();
    }

    private PlayerProtos.Player player(int id, String fullName, String shortName, PlayerProtos.SportType sportType) {
        return PlayerProtos.Player.newBuilder()
                .setFullName(fullName)
                .setShortName(shortName)
                .setId(id)
                .setSportType(sportType)
                .build();
    }
}
