<template>
    <div class="next-game-container centred sidebar-card" v-on:click="highlightInCalendar">
        <p>NEXT GAME</p>
        <div class="next-game-info centred">
            <div class="team away centred">
                <div class="image">
                    <img :alt="awayName" :src="away.image">
                </div>
                <p class="team-name">{{ awayName }}</p>
            </div>
            <span>@</span>
            <div class="team home centred">
                <div class="image">
                    <img :alt="homeName" :src="home.image">
                </div>
                <p class="team-name">{{ homeName }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import { fullTeamName } from "../../../../../assets/util.ts";


export default {
    name: "next-game-card",
    computed: {
        date() {
            const today = new Date();
            return {
                day: today.getDate() + 1,
                month: today.getMonth() + 1,
                year: today.getFullYear()
            };
        },
        nextGame() {
            return {
                home: {
                    image: "",
                    name: "Lakers",
                    city: "Los Angeles",
                },
                away: {
                    image: "",
                    name: "Nets",
                    city: "Brooklyn",
                }
            };
        },
        home() {
            return this.nextGame.home;
        },
        away() {
            return this.nextGame.away;
        },
        homeName() {
            return fullTeamName(this.home.city, this.home.name);
        },
        awayName() {
            return fullTeamName(this.away.city, this.away.name);
        }

    },
    methods: {
        highlightInCalendar() {
            const event = new CustomEvent("dateclick", {
                detail: {
                    day: this.date.day,
                    month: this.date.month,
                    year: this.date.year,
                }
            });
            window.dispatchEvent(event);
        }
    }
};
</script>

<style scoped>
.next-game-container {
    flex-direction: column;
    background: var(--flat-light-red);
    cursor: pointer;
}

.next-game-container p {
    font-weight: 700;
    text-align: left;
}

.next-game-info {
    align-items: flex-start;
}

.next-game-info span, .next-game-info p {
    font-weight: 700;
    color: #FFF;
    margin-top: 0;
    margin-bottom: 0;
}

.next-game-info span {
    margin-left: 16px;
    margin-right: 16px;
    font-size: 24px;
    margin-top: 14px;
}

.team {
    flex-direction: column;
    flex: 1;
}

.team .image {
    border-radius: 50%;
    background: var(--light-blue);
    width: 56px;
    height: 56px;
}

.team .team-name {
    margin-top: 6px;
    text-align: center;
    font-size: 12px;
}
</style>