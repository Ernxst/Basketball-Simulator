import Reference from "firebase-storage-lite";
import {routes} from "../router/routes.js";
import {store} from "../store/store.js";

const base = "gs://basketball-simulator-4f6a7.appspot.com/";
const baseBucket = new Reference(base);
const imgFormat = ".webp";

async function getImage(src, bucket) {
    const imageRef = bucket.child(src + imgFormat);
    return imageRef.getDownloadURL().then(
        url => {
            return url;
        },
        error => {
            return "";
        }
    );
}

const fetchImages = async (folder, names) => {
    const map = {};
    const bucket = await baseBucket.child(folder + "/");
    for (const name of names) {
        map[name] = await getImage(name, bucket);
    }
    return map;
};

class MediaService {
    async fetchBackgrounds() {
        const names = [];
        for (const route of routes) {
            if (route.name) {
                names.push(route.name);
            }
        }
        if (Object.values(store.getters["media/backgrounds"]).length < names.length)
            return fetchImages("backgrounds", names);
        return store.getters["media/backgrounds"]
    }
}

export default new MediaService();