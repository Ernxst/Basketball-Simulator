import { StringStringMap } from "@/@types/maps";
import Reference from "firebase-storage-lite";

const base: string = "gs://basketball-simulator-4f6a7.appspot.com/";
const baseBucket: Reference = new Reference(base);
const imgFormat: string = ".webp";

/**
 * Return the download URL of a given file.
 * @param src name of the filename.
 * @param bucket the Firebase bucket where the image is stored.
 * @returns a Promise, resolving to the download URL of the image.
 */
async function getImage(src: string, bucket: any): Promise<string> {
  const imageRef = bucket.child(src + imgFormat);
  return imageRef.getDownloadURL().then(
    (url: string) => {
      return url;
    },
    (error: Error) => {
      return "";
    }
  );
}

/**
 *
 * @param folder the name of the folder where the images are located.
 * @param names the images to fetch.
 * @returns a map of filenames to their Firebase download URLs.
 */
const fetchImages = async (
  folder: string,
  names: Array<string>
): Promise<StringStringMap> => {
  const map: StringStringMap = {};
  const bucket = await baseBucket.child(folder + "/");
  for (const name of names) {
    map[name] = await getImage(name, bucket);
  }
  return map;
};

class MediaService {
  async fetchBackgrounds(names: Array<string>): Promise<StringStringMap> {
    return fetchImages("backgrounds", names);
  }
}

export default new MediaService();
