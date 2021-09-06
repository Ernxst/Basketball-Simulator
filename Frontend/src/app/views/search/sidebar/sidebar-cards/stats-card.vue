<template>
    <div class="stats-card centred sidebar-card">

    </div>
</template>

<script>
export default {
    name: "stats-card",
    props: {
        searchInfo: Object
    }
};
</script>

<style scoped>
.stats-card {
    flex-direction: column;
    background: var(--flat-green);
}
</style>