FROM docker.io/node:lts-alpine

ENV HOST=0.0.0.0
ENV PORT=3000

WORKDIR /app

RUN addgroup --system bookings-reservations-subgraph && \
    adduser --system -G bookings-reservations-subgraph bookings-reservations-subgraph

ADD dist/apps/bookings-reservations-subgraph/package.json bookings-reservations-subgraph/
ADD dist/apps/bookings-reservations-subgraph/pnpm-lock.yaml bookings-reservations-subgraph/

RUN corepack pnpm --prefix bookings-reservations-subgraph --prod install

COPY dist/apps/bookings-reservations-subgraph/reservations bookings-reservations-subgraph/
ADD dist/apps/bookings-reservations-subgraph/main.js bookings-reservations-subgraph/

RUN chown -R bookings-reservations-subgraph:bookings-reservations-subgraph .

CMD [ "node", "bookings-reservations-subgraph" "main.js" ]
