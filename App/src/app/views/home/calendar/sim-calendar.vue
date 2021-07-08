<template>
    <calendar-container>
        <div ref="calendar" class="sim-calendar"></div>
    </calendar-container>
</template>

<script>
import { Calendar } from "@fullcalendar/core";
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import CalendarContainer from "./calendar-container.vue";


export default {
    name: "sim-calendar",
    components: { CalendarContainer },
    data() {
        return {
            calendar: null,
            activeEl: null,
            sidebarClickEvents: [],
            scrollingElement: null,
        };
    },
    computed: {
        currentGameDate() {
            const date = new Date();
            return {
                day: date.getDate(),
                month: date.getMonth() + 1,
                year: date.getFullYear() - 1
            };
        },
        currentGameYear() {
            return this.currentGameDate.year;
        },
        events() {
            const tomorrow = new Date();
            const today = new Date;
            tomorrow.setDate(new Date().getDate() + 1);
            const tomorrowStr = tomorrow.toISOString().replace(/T.*$/, ''); // YYYY-MM-DD of today
            const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

            function formatDate(dateStr) {
                const date = new Date(Date.parse(dateStr));
                return {
                    day: date.getDate(),
                    month: months[date.getMonth()],
                    year: date.getFullYear(),
                };
            }

            return [
                {
                    id: 1,
                    title: 'Game Day',
                    start: tomorrowStr + 'T12:00:00',
                    allDay: true,
                    extendedProps: {
                        components: formatDate(tomorrowStr + 'T12:00:00'),
                        description: 'Game Day',
                    },
                    display: "background"
                }
            ];
        }
    },
    methods: {
        removeHighlight() {
            if (this.activeEl !== null) {
                this.activeEl.classList.remove("game-day");
                this.activeEl = null;
            }
        },
        prependZeroToDate(date) {
            return (date < 10) ? "0" + date.toString() : date;
        },
        highlightDate(day, month, year) {
            const date = `${year}-${this.prependZeroToDate(month)}-${this.prependZeroToDate(day)}`;
            console.log(date);
            this.sidebarClickEvents.push({ day, month, year });
            this.calendar.gotoDate(date);
            const selector = `[data-date="${date}"]`;
            this.activeEl = document.querySelector(selector);
            this.activeEl.classList.add("game-day");
            const offset = this.activeEl.offsetTop;
            const height = this.activeEl.clientHeight;
            const top = offset + (height / 2);
            this.scrollingElement.scrollTo({
                top: top,
                behavior: "smooth"
            });
        },
        // TODO - Show popup to sim to date
        handleDateClick(dateClickInfo) {
            this.removeHighlight();
            console.log({ dateClickInfo });
        },

        // TODO - Show popup to sim to date
        handleEventClick(clickInfo) {
            this.removeHighlight();
            console.log({ clickInfo });
        },
        handleEvents(events) {
            if (this.sidebarClickEvents.length === 0)
                this.removeHighlight();
            // console.log({ events });
            this.sidebarClickEvents.pop();
        },
        initCalendar() {
            const calendarEl = this.$refs.calendar;
            this.calendar = new Calendar(calendarEl, {
                plugins: [interactionPlugin, dayGridPlugin],
                firstDay: 1,
                dayCellClassNames: ["day-cell"],
                height: "100%",
                initialEvents: this.events, // alternatively, use the `events` setting to fetch from a feed
                weekends: true,
                dateClick: this.handleDateClick,
                eventClick: this.handleEventClick,
                eventsSet: this.handleEvents,
                headerToolbar: {
                    left: 'title',
                    right: 'prev,next today',
                },
                validRange: {
                    start: `${this.currentGameYear}-10-22`,
                    end: `${this.currentGameYear + 1}-06-13`
                }
            });
            this.calendar.render();
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.scrollingElement = document.querySelector("main");
            this.initCalendar();
            window.addEventListener("dateclick", (e) => {
                this.highlightDate(e.detail.day, e.detail.month, e.detail.year);
            });
        });
    }
};
</script>

<style>
.sim-calendar thead {
    background: var(--blue) !important;
}

.sim-calendar thead th {
    padding: 8px 0;
}

.sim-calendar .day-cell {
    cursor: pointer !important;
}

.game-day {
    background: var(--flat-light-red) !important;
    transition-timing-function: ease-in;
    transition-property: background-color;
    transition-duration: .3s;
}

.sim-calendar .day-cell:hover {
    background: var(--pale-blue) !important;
    transition: none !important;
}

.sim-calendar .day-cell:hover * {
    border-color: var(--pale-blue) !important;
    background: var(--pale-blue) !important;
}

.fc-bg-event {
    background: var(--dark-blue) !important;
}

.game-day .fc-bg-event, .game-day {
    background: var(--flat-light-red) !important;
    border-color: var(--flat-light-red) !important;
    border-width: 2px !important;
}

.sim-calendar .game-day.day-cell:hover {
    border-color: var(--pale-blue) !important;
    background: var(--pale-blue) !important;
}
</style>