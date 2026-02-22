# Crypto AI Android Screen

This repository contains a Jetpack Compose Android screen that:

- Shows a list of crypto indices.
- Uses **green** text when an index is up and **red** text when it is down.
- Displays an AI status message indicating continuous market analysis.
- Shows AI-generated buy/sell/hold predictions on the same screen.

## Notes

- `CryptoRepository` currently simulates API streaming data.
- Replace it with your real API integration and map your API trend identifier to `changePercent24h`.
