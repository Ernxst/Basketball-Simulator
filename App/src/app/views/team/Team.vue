<template>
    <page>
        <template v-slot:sidebarView>
            <team-sidebar></team-sidebar>
        </template>
            <h1>My Team</h1>
            <p></p>
    </page>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import TeamSidebar from "./sidebar/team-sidebar.vue";

export default {
    name: "Team",
    components: { TeamSidebar, Page },
};
</script>

<style scoped>
</style>