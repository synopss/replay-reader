# Replay Reader
This application is World of Tanks replay analysis tool focusing on important statics to help competitive teams to better understand their player performances

## Tanks
To update the tanks' list, first update the `.\tools\tanks.json` by running the Wargaming's API with those parameters and your API key:
```
https://api.worldoftanks.eu/wot/encyclopedia/vehicles/?application_id={{application_key}}=name%2Ctag%2Cnation
```

you need to run the command to convert the json output into a readable properties file for the application:

```bash
python .\tools\tanks-exporter.py .\tools\tanks.json .\src\main\resources\tanks.properties
```