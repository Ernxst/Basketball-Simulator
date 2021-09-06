<template>
    <div ref="container" class="calendar-container centred noselect">
        <slot></slot>
    </div>
</template>

<script>
export default {
    name: "calendar-container"
};
</script>

<style>
.calendar-container {
    height: 100%;

    --fc-page-bg-color: var(--page-bg-color);
    --fc-neutral-bg-color: var(--black);
    --fc-neutral-text-color: var(--black);
    --fc-border-color: var(--blue);

    --fc-button-bg-color: var(--blue) !important;
    --fc-button-border-color: var(--blue) !important;
    --fc-button-hover-bg-color: var(--pale-blue);
    --fc-button-hover-border-color: var(--pale-blue);
    --fc-button-active-bg-color: var(--turqoise) !important;
    --fc-button-active-border-color: var(--turqoise) !important;

    --fc-event-bg-color: var(--dark-blue) !important;
    --fc-event-border-color: var(--dark-blue);
    --fc-bg-event-opacity: 1;

    --fc-highlight-color: rgba(188, 232, 241, 0.3);
    --fc-today-bg-color: var(--turqoise);
}

.calendar-container > * {
    width: 100%;
}
</style>