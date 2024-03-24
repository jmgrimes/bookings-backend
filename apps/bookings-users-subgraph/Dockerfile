FROM docker.io/node:lts-alpine

ENV HOST=0.0.0.0
ENV PORT=3000

WORKDIR /app

RUN addgroup --system bookings-users-subgraph && \
    adduser --system -G bookings-users-subgraph bookings-users-subgraph

ADD dist/apps/bookings-users-subgraph/package.json bookings-users-subgraph/
ADD dist/apps/bookings-users-subgraph/pnpm-lock.yaml bookings-users-subgraph/

RUN corepack pnpm --prefix bookings-users-subgraph --prod install

COPY dist/apps/bookings-users-subgraph/users bookings-users-subgraph/
ADD dist/apps/bookings-users-subgraph/main.js bookings-users-subgraph/

RUN chown -R bookings-users-subgraph:bookings-users-subgraph .

CMD [ "node", "bookings-users-subgraph" "main.js" ]
