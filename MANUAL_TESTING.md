# Slabwise manual test checklist

Automated GameTests cover geometry, representative placement and merging, waterlogging, double drops, connection geometry, center support, and a dependent-block neighbor update. Before release, also run this short in-game pass:

- Place every material from the Slabwise creative tab and scan the log for missing-model or missing-texture warnings.
- Place each orientation from horizontal faces, block tops, and block undersides; check exact-corner tie behavior from multiple player facings.
- Merge every material in both valid axes and verify invalid perpendicular pairs place in the adjacent block instead.
- Waterlog each orientation with a bucket, merge a waterlogged half, break it, and verify fluid behavior and two-item drops.
- Check fences, nether brick fences, walls, iron bars, and glass panes against both occupied and empty outer faces.
- Check wall torches, redstone torches, buttons, levers, ladders, wall signs, hanging signs, banners, and tripwire hooks on occupied versus empty faces.
- Check lanterns and soul lanterns above and below half and double slabs; vanilla dependent-block models should remain centered.
- Check pressure plates, rails, redstone dust, repeaters, and comparators on top of all four halves and a double slab.
- Compare mining speed, required tool tier, sound, piston response, note-block instrument, blast resistance, light, and redstone conduction with each source vanilla slab.
- Ignite Overworld wood and bamboo variants; verify crimson, warped, and petrified oak variants do not burn.
- Let unwaxed copper variants oxidize, wax them with honeycomb, and scrape oxidation/wax with an axe while confirming orientation and waterlogging are preserved.
- Run both a dedicated server and a client against it to catch side-only classloading mistakes.
