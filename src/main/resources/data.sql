INSERT INTO db_roles(id, name)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a1', 'USER'),
       ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 'ADMIN'),
       ('798060ff-ed9b-4745-857c-a7a2f4a2e3a3', 'DISPATCHER');

INSERT INTO flats(id, city, street, house_number, entrance, flat_number)
VALUES ('695aecfe-152f-421d-9c4b-5831b812cd21', 'Екатеринбург', 'Белинского', '5', 2, 40),
       ('695aecfe-152f-421d-9c4b-5831b812cd22', 'Екатеринбург', 'Белинского', '5', 1, 10),
       ('695aecfe-152f-421d-9c4b-5831b812cd23', 'Екатеринбург', 'Машинная', '44', 2, 10);


INSERT INTO gages(id, serial_number, flat_id, type_gage, manufacturer, installation_date)
VALUES ('5f8c8fb4-503a-4727-a47a-19d539fa6571', '1234567', '695aecfe-152f-421d-9c4b-5831b812cd21', 'THERMAL_ENERGY', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6572', '1234568', '695aecfe-152f-421d-9c4b-5831b812cd21', 'ELECTRICAL_ENERGY', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6573', '1234510', '695aecfe-152f-421d-9c4b-5831b812cd21', 'WATER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6574', '2345671', '695aecfe-152f-421d-9c4b-5831b812cd22', 'THERMAL_ENERGY', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6575', '2345681', '695aecfe-152f-421d-9c4b-5831b812cd22', 'ELECTRICAL_ENERGY', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6576', '2345101', '695aecfe-152f-421d-9c4b-5831b812cd22', 'WATER', 'Энергомир', '2022-06-01');

INSERT INTO contract(id, contract_number, signed_date, flat_id)
VALUES ('3cd5a670-7888-11ed-a1eb-0242ac120001', '00500110122021', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd21'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120002', '00500210122022', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd22'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120003', '00000000000000', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd23'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120004', '10000000000000', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd23');

INSERT INTO db_users(id, password, username, contract_id)
VALUES ('6235e344-795f-11ed-a1eb-0242ac120001', '$2a$10$s.Sth7cC4F8z/6boyBJ8hOwtBuyLTfWITLV0l/LlUbAPstTCVfRta', 'admin', '3cd5a670-7888-11ed-a1eb-0242ac120003'),
       ('29b59ddb-df71-4955-9af9-12aaa4dd8653', '$2a$10$LudFSdqj3PQCYv9Eo4AToubEmf6SHFb3beSd4/SZROmohjpxYhwjK', 'vika', '3cd5a670-7888-11ed-a1eb-0242ac120001'),
       ('3d8c7ed8-2300-41be-85e9-bd04dc26d1a9', '$2a$10$LudFSdqj3PQCYv9Eo4AToubEmf6SHFb3beSd4/SZROmohjpxYhwjK', 'disp', '3cd5a670-7888-11ed-a1eb-0242ac120004');

INSERT INTO user_roles(user_id, role_id)
VALUES ('6235e344-795f-11ed-a1eb-0242ac120001', '798060ff-ed9b-4745-857c-a7a2f4a2e3a2'),
       ('29b59ddb-df71-4955-9af9-12aaa4dd8653', '798060ff-ed9b-4745-857c-a7a2f4a2e3a1'),
       ('3d8c7ed8-2300-41be-85e9-bd04dc26d1a9', '798060ff-ed9b-4745-857c-a7a2f4a2e3a3');

INSERT INTO gages_data(id, gage_id, user_id, data, departure_date)
VALUES ('c6346318-7cc8-11ed-a1eb-0242ac120001', '5f8c8fb4-503a-4727-a47a-19d539fa6571', '29b59ddb-df71-4955-9af9-12aaa4dd8653', '810', '2023-01-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120002', '5f8c8fb4-503a-4727-a47a-19d539fa6572', '29b59ddb-df71-4955-9af9-12aaa4dd8653', '400', '2023-01-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120003', '5f8c8fb4-503a-4727-a47a-19d539fa6572', '29b59ddb-df71-4955-9af9-12aaa4dd8653', '250', '2023-01-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120004', '5f8c8fb4-503a-4727-a47a-19d539fa6573', '29b59ddb-df71-4955-9af9-12aaa4dd8653', '100', '2023-01-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120005', '5f8c8fb4-503a-4727-a47a-19d539fa6573', '29b59ddb-df71-4955-9af9-12aaa4dd8653', '450', '2023-01-23');

INSERT INTO claims(id, user_id, description, creation_date, status)
VALUES ('e5486bb2-a26d-11ed-a8fc-0242ac120001', '29b59ddb-df71-4955-9af9-12aaa4dd8653', 'не работатет лифт', '2023-01-23', 'ACTIVE'),
