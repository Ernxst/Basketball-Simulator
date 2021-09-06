class ConstantsService {
    maxLeaguesPerAccount(): number {
        return 3;
    }

    minTeamsInLeague(): number {
        return 10;
    }

    maxTeamsInLeague(): number {
        return 45;
    }
}

export default new ConstantsService();