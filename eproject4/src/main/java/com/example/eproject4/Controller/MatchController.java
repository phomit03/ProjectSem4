package com.example.eproject4.Controller;

import com.example.eproject4.Entity.Match;

public class MatchController {
    public void createMatch(Match match) {
        // Tạo một trận đấu mới
        match.save();
    }

    public Match getMatch(int matchId) {
        // Lấy thông tin về một trận đấu dựa trên ID
        Match match = Match.get(matchId);
        return match;
    }

    public void updateMatch(int matchId, Match matchData) {
        // Cập nhật thông tin trận đấu dựa trên ID
        Match match = Match.get(matchId);
        if (match != null) {
            match.update(matchData);
        } else {
            // Xử lý khi không tìm thấy trận đấu
            System.out.println("Không tìm thấy trận đấu");
        }
    }

    public void deleteMatch(int matchId) {
        // Xóa một trận đấu dựa trên ID
        Match match = Match.get(matchId);
        if (match != null) {
            match.delete();
        } else {
            // Xử lý khi không tìm thấy trận đấu
            System.out.println("Không tìm thấy trận đấu");
        }
    }
}
