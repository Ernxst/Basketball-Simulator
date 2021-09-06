<template>
    <div class="home-sidebar">
        <next-game-card></next-game-card>
    </div>
</template>

<script>

import NextGameCard from "./sidebar-cards/next-game-card.vue";


export default {
    name: "home-sidebar",
    components: { NextGameCard },
};
</script>

<style scoped>
.home-sidebar {

}

</style>