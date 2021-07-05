<template>
    <div class="team-info centred sidebar-card">
        <div class="image">
            <img :alt="`The ${city} ${name}' logo`" :src="image">
        </div>
        <div class="team-content">
            <div class="team-city">{{ city }}</div>
            <div class="team-name">{{ name }}</div>
            <div class="team-record">
                <span class="wins">{{ wins }}</span><span class="record-separator">-</span><span
                class="losses">{{ losses }}</span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "team-info",
    computed: {
        teamData() {
            return {
                city: "Los Angeles",
                name: "Lakers",
                image: "",
                wins: 62,
                losses: 20,
            };
        },
        wins() {
            return this.teamData["wins"];
        },
        losses() {
            return this.teamData["losses"];
        },
        city() {
            return this.teamData["city"];
        },
        name() {
            return this.teamData["name"];
        },
        image() {
            return this.teamData["image"];
        }
    }
};
</script>

<style scoped>
.team-info {
}

.team-info .image {
    background: blue;
    border-radius: 50%;
    width: 76px;
    height: 76px;
    overflow: hidden;
    margin-right: 24px;
    border: 1px solid #FFF;
    box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
}

.team-content * {
    white-space: nowrap;
    text-transform: capitalize;
}

.team-city, .team-name {
    overflow: hidden;
    text-overflow: ellipsis;
}

.team-name {
    font-weight: 600;
    color: #FFF;
    font-size: 21px;
    margin-bottom: 4px;
}

.team-city {
    color: #D9D9D9;
    font-size: 14px;
}

.team-record {
    display: inline-block;
    padding: 4px 6px;
    background: var(--blue);
    border-radius: 4px;
}

.team-record, .team-record > * {
    font-size: 12px;
    font-weight: 600;
}

.team-record .record-separator {
    margin-left: 4px;
    margin-right: 4px;
}

.team-record .wins {
    color: var(--electric-green);
}

.team-record .losses {
    color: var(--flat-light-red);
}
</style>