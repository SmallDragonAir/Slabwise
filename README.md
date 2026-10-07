# Slabwise

Slabwise is a NeoForge mod for Minecraft Java Edition 1.21.1 that adds a vertical counterpart for every vanilla slab.

The implementation follows vanilla slab conventions: exact half-block geometry, hit-position placement, complementary merging, waterlogging, two-item double-slab drops, and geometry-driven support checks.

## Development

Requires Java 21.

```text
./gradlew build
./gradlew runData
./gradlew runGameTestServer
./gradlew runClient
```

Generated assets and data are written to `src/generated/resources` and are included in normal builds.

See [MANUAL_TESTING.md](MANUAL_TESTING.md) for the in-game verification checklist.
