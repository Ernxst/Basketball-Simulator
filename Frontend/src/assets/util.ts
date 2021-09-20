/**
 *
 * @param city
 * @param name
 */
export function fullNameToPossessiveName(city: string, name: string): string {
  return `${city} ${nameToPossessiveName(name)}`;
}

/**
 * Converts a team name into its possessive form.
 * @param name
 * @returns {string}
 */
export function nameToPossessiveName(name: string): string {
  if (name.endsWith("s")) {
    return `${name}'`;
  }
  return `${name}'s`;
}

/**
 * Helper method to merge city and team name into a single string.
 * @param city
 * @param name
 */
export function fullTeamName(city: string, name: string): string {
  return `${city} ${name}`;
}

/**
 *
 * @param obj the item to test.
 * @param keyName the key @obj is stored under.
 * @param map the map the object is stored in.
 * @returns whether or not an item is of type Object.
 */
function isObject(obj: any, keyName: string, map: Record<any, any>): boolean {
  if (obj === null) return false;
  try {
    return obj.constructor === Object;
  } catch {
    console.log({ obj, keyName, map });
    return false;
  }
}

/**
 * Convert all string key names in a map to kebab-case, including any nested maps.
 * @param map the map to convert to kebab-case.
 * @returns a map where all key names are in kebab-case.
 */
export function toKebabCaseMap(map: Record<string, any>): Record<any, any> {
  const formattedMap: Record<string, any> = {};
  for (const [key, value] of Object.entries(map)) {
    formattedMap[toKebabCase(key)] = isObject(value, key, map)
      ? toKebabCaseMap(value)
      : value;
  }
  return formattedMap;
}

/**
 * Convert all string key names in a map to snake_case, including any nested maps.
 * @param map the map to convert to snake_case.
 * @returns a map where all key names are in snake_case.
 */
export function toSnakeCaseMap(map: Record<string, any>): Record<string, any> {
  const formattedMap: Record<string, any> = {};
  for (const [key, value] of Object.entries(map)) {
    formattedMap[toSnakeCase(key)] = isObject(value, key, map)
      ? toSnakeCaseMap(value)
      : value;
  }
  return formattedMap;
}

/**
 * Convert a string into snake_case.
 * @param str
 * @returns the string in snake_case form.
 */
export const toSnakeCase = (str: string): string => {
  const formatted = str.replace(
    /[A-Z]/g,
    (letter) => `_${letter.toLowerCase()}`
  );
  return formatted.replace(/[-]/g, "_");
};

/**
 * Convert a string into kebab-case.
 * @param str
 * @returns the string in kebab-case form.
 */
export const toKebabCase = (str: string): string => {
  return str
    .split("")
    .map((letter, idx) => {
      if (isNaN(Number(letter))) {
        return letter.toUpperCase() === letter
          ? `${idx !== 0 ? "-" : ""}${letter.toLowerCase()}`
          : letter;
      }
      return letter;
    })
    .join("")
    .replace(/[_]/g, "")
    .replace(/--/g, "-");
};

/**
 * Returns the similarity between two strings.
 * @param s1 {string} input string.
 * @param s2 {string} input string.
 * @returns {number} the similarity, as a decimal, between s1 and s2.
 */
export function similarity(s1: string, s2: string): number {
  let longer = s1;
  let shorter = s2;
  if (s1.length < s2.length) {
    longer = s2;
    shorter = s1;
  }
  const longerLength = longer.length;
  if (longerLength === 0) {
    return 1.0;
  }
  return (longerLength - editDistance(longer, shorter)) / longerLength;
}

/**
 * Return the edit distance between two strings.
 * @param s1 {string} input string.
 * @param s2 {string} input string.
 * @returns {number} edit distance between the two strings.
 */
function editDistance(s1: string, s2: string): number {
  s1 = s1.toLowerCase();
  s2 = s2.toLowerCase();

  const costs = [];
  for (let i = 0; i <= s1.length; i++) {
    let lastValue = i;
    for (let j = 0; j <= s2.length; j++) {
      if (i === 0) costs[j] = j;
      else {
        if (j > 0) {
          let newValue = costs[j - 1];
          if (s1.charAt(i - 1) !== s2.charAt(j - 1))
            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
          costs[j - 1] = lastValue;
          lastValue = newValue;
        }
      }
    }
    if (i > 0) costs[s2.length] = lastValue;
  }
  return costs[s2.length];
}
