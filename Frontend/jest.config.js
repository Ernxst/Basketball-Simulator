const patterns = () => {
  const packages = [""];
  const base = "/node_modules/(?!{PACKAGE})/";
  const ignorePatterns = [];
  for (const packageName of packages) {
    const pattern = base.replace("{PACKAGE}", packageName);
    ignorePatterns.push(pattern);
  }
  return ignorePatterns;
};

module.exports = {
  clearMocks: true,
  transform: {
    "node_modules/variables/.+\\.(j|t)sx?$": "ts-jest",
    ".*\\.(vue)$": "vue-jest",
    "^.+\\.tsx?$": "ts-jest",
    ".*\\.(js)$": "babel-jest",
  },
  moduleNameMapper: {
    "@/(.*)": "<rootDir>/src/$1",
  },
  transformIgnorePatterns: patterns(),
  collectCoverage: true,
  collectCoverageFrom: ["src/**/*.ts", "src/**/*.js", "src/**/*.vue"],
  testURL: "http://localhost/",
};
