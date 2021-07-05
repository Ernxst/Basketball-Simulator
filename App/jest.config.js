const patterns = () => {
    const packages = [""];
    const base = "/node_modules/(?!{PACKAGE})";
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
        ".*\\.(vue)$": "vue-jest",
        ".*\\.(js)$": "babel-jest",
    },
    transformIgnorePatterns: patterns(),
    collectCoverage: true,
    collectCoverageFrom: ["src/**/*.js", "src/**/*.vue"],
};
