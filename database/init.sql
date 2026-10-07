create table Customer (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20)
);

create table Customer (
    id serial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20)
);

insert into Customer (name, email, phone)
values  ('Ivan Ivanov', 'ivan@example.com', '+79991112233'),
        ('Petr Petrov', 'petr@example.com', '+79992223344'),
        ('Anna Smirnova', 'anna@example.com', '+79993334455');

insert into Orders (order_date, total_sum, status, customer_id)
values ('2026-10-01', 1500.00, 'ready', 10),
       ('2026-10-02', 2750.50, 'processing', 10),
       ('2026-10-03', 850.00, 'not ready', 11),
       ('2026-10-04', 4200.00, 'ready', 12);