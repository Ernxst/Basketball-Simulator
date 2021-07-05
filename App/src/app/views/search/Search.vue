<template>
    <page>
        <template v-slot:sidebarView>
            <search-sidebar></search-sidebar>
        </template>
        <h1>Search League</h1>
        <p>Search the league for players, specifying the search criteria in the sidebar on the left.</p>
        <section id="search-results">

        </section>
    </page>
</template>

<script>
import Page from "../../components/layout/Page.vue";
import SearchSidebar from "./sidebar/search-sidebar.vue";

export default {
    name: "Search",
    components: { SearchSidebar, Page },
    methods: {
        search() {

        }
    }
};
</script>

<style scoped>
</style>