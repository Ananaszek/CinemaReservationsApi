#!/usr/bin/env bash
curl http://localhost:8888/cinema/showMovies/2019-05-02%2009:00:00/2019-05-20%2009:00:00/
curl http://localhost:8888/cinema/selectScreening/In%20your%20face/2019-05-03%2015:00:00/
curl -d '{"screeningId":"3", "seatsSignatures":["A1","A2"], "ticketTypes":["A","S"],"bookerName":"Jan","bookerSurname":"Kowalski"}' -H "Content-Type: application/json" -X POST http://localhost:8888/cinema/summary
