<template>
    <div class="player-info-card centred sidebar-card">
        <autocomplete-input id="first-name-input" icon="person" label="First Name" placeholder="Kyrie"
                            :suggestions="allFirstNames" :object="searchInfo" type="text"
                            key-name="first-name"></autocomplete-input>
        <autocomplete-input id="last-name-input" icon="person" label="Last Name" placeholder="Irving"
                            :suggestions="allLastNames" :object="searchInfo" type="text"
                            key-name="last-name"></autocomplete-input>
    </div>
</template>

<script>
import AutocompleteInput from "../../../../components/widgets/text-input/autocomplete-input.vue";

export default {
    name: "player-info-card",
    components: { AutocompleteInput },
    props: {
        searchInfo: Object
    },
    computed: {
        allFirstNames() {
            return ["Kyrie", "LeBron", "Kevin", "Paul", "Kawhi", "Chris", "Devin",
                "Jayson", "Jaylen", "Nikola", "Luka", "Jamal", "Anthony", "Ben", "Joel", "Stephen", "Damian"];
        },
        allLastNames() {
            return ["Irving", "James", "Durant", "George", "Leonard", "Paul", "Booker", "Tatum", "Brown",
            "Jokic", "Doncic", "Murray", "Davis", "Simmons", "Embiid", "Curry", "Lillard"];
        }
    }
};
</script>

<style>
.search-sidebar .player-info-card {
    flex-direction: column;
    background: var(--pale-blue);
}

.search-sidebar .player-info-card > *:not(:last-child) {
    margin-bottom: 16px;
}

.search-sidebar .player-info-card .text-input label {
    color: #FFF;
}
</style>