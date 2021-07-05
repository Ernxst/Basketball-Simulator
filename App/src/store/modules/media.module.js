import Reference from "firebase-storage-lite";

const base = "gs://fise-concierge.appspot.com/";
const baseBucket = new Reference(base);
const imgFormat = ".webp";

async function getImage(src, bucket) {
    try {
        const imageRef = bucket.child(src + imgFormat);
        return await imageRef.getDownloadURL();
    } catch {
        return null;
    }
}

const fetchImages = async (folder, names) => {
    const map = {};
    const bucket = await baseBucket.child(folder + "/");
    for (const name of names) {
        map[name] = await getImage(name, bucket);
    }
    return map;
};