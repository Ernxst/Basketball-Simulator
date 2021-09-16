<template>
    <div class="league-card centred noselect">
        <div
            v-if="teamName !== undefined"
            class="card-content centred"
            @click="select"
        >
            <span class="close-icon material-icons" v-on:click="deleteLeague"
                >close</span
            >
            <div class="card-img image">
                <img :alt="teamName" :src="background" />
            </div>
            <div class="league-data centred">
                <img :src="teamIcon" :alt="teamName" />
                <span class="league-name">{{ leagueName }}</span>
                <span class="team-state">{{ teamState }}</span>
                <span class="team-name">{{ teamName }}</span>
                <span class="wl-record"
                    ><b style="color: var(--flat-green)">{{ wins }}</b> -
                    <b style="color: var(--flat-red)">{{ losses }}</b></span
                >
                <table class="league-info">
                    <tr>
                        <td>Game Date</td>
                        <td class="current-game-date">
                            <b>{{ currentDate }}</b>
                        </td>
                    </tr>
                    <tr>
                        <td>Season</td>
                        <td class="current-season">
                            <b>{{ currentSeason }}</b>
                        </td>
                    </tr>
                    <tr>
                        <td>Last Played</td>
                        <td class="last-played">
                            <b>{{ lastPlayed }}</b>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <empty-league-card
            @newLeague="$emit('newLeague')"
            v-else
        ></empty-league-card>
    </div>
</template>

<script>
    import EmptyLeagueCard from "./empty-league-card.vue";

    export default {
        name: "league-card",
        components: { EmptyLeagueCard },
        props: {
            leagueID: Number,
            leagueName: String,
            teamState: String,
            teamName: String,
            iconID: Number,
            currentDate: String,
            currentSeason: Number,
            lastPlayed: String,
            wins: Number,
            losses: Number,
        },
        computed: {
            background() {
                return this.$store.getters["media/backgrounds"]["welcome"];
            },
            teamIcon() {
                return "";
            },
        },
        methods: {
            onMouseEnter() {},
            onMouseLeave() {},
            select() {
                this.$store.commit("league/setCurrentLeagueID", this.leagueID);
                this.$router.push({
                    name: "play",
                    params: {
                        league_id: this.leagueID,
                    },
                });
            },
            deleteLeague() {
                if (
                    confirm(
                        `Are you sure you want to delete ${this.leagueName}? All progress will be lost - this action cannot be undone.`
                    )
                );
            },
        },
    };
</script>

<style scoped>
    .league-card,
    .card-content,
    .league-data {
        flex-direction: column;
    }

    .league-card:hover {
        transform: translateY(-24px);
    }

    .league-card,
    .card-content {
        width: 100%;
        height: 100%;
    }

    .league-card {
        border-radius: var(--card-radius);
        cursor: pointer;
        border: 1px solid #fff;
        transition: 0.2s ease-in-out transform;
    }

    .card-content {
        position: relative;
        justify-content: flex-end;
    }

    .close-icon {
        background: var(--flat-red);
        color: #000;
        border-radius: 50%;
        position: absolute;
        top: 16px;
        right: 16px;
        padding: 2px;
        z-index: 3;
    }

    .close-icon:hover {
        filter: brightness(70%);
    }

    .card-img,
    .card-img:after {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .card-img {
        z-index: 0;
        overflow: hidden;
        border-radius: var(--card-radius);
    }

    .card-img img {
        max-width: unset;
        height: 100%;
        object-fit: cover;
        width: 100%;
    }

    .card-img::after {
        content: "";
        background: linear-gradient(0deg, #00000088 30%, #ffffff44 100%);
        width: 100%;
        height: 100%;
        z-index: 1;
    }

    .league-data {
        width: 100%;
        align-items: flex-start;
        justify-content: flex-end;
        z-index: 2;
        padding: 24px;
    }

    .league-data * {
        text-align: left;
        width: 100%;
    }

    .league-name,
    .team-state,
    .team-name {
        text-transform: uppercase;
        font-weight: 700;
    }

    .league-name {
        font-size: 32px;
        letter-spacing: 2px;
        margin-bottom: 12px;
    }

    .team-state {
        font-size: 13px;
        margin-bottom: 0;
        color: #f1f1f1;
        letter-spacing: 2px;
    }

    .team-name {
        font-size: 24px;
        padding-bottom: 12px;
        margin-bottom: 12px;
        border-bottom: 1px solid #fff;
    }

    .wl-record {
        font-size: 16px;
    }

    .league-info td {
        white-space: nowrap;
    }

    .league-info tr td:first-child {
        text-align: left;
    }

    .league-info tr td:last-child {
        text-align: right;
    }
</style>
