<template>
    <page>
        <template v-slot:sidebarView>
            <insights-sidebar></insights-sidebar>
        </template>
        <h1>Insights</h1>
    </page>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import InsightsSidebar from "./sidebar/insights-sidebar.vue";


export default {
    name: "Insights",
    components: { InsightsSidebar, Page },
};
</script>

<style scoped>
</style>