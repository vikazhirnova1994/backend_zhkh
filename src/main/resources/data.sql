INSERT INTO db_roles(id, name)
VALUES ('798060ff-ed9b-4745-857c-a7a2f4a2e3a1', 'USER'),
       ('798060ff-ed9b-4745-857c-a7a2f4a2e3a2', 'MODERATOR'),
       ('798060ff-ed9b-4745-857c-a7a2f4a2e3a3', 'ADMIN'),
       ('798060ff-ed9b-4745-857c-a7a2f4a2e3a4', 'DISPATCHER');

INSERT INTO flats(id, area, city, street, house_number, entrance, flat_number)
VALUES ('695aecfe-152f-421d-9c4b-5831b812cd21', 25.00, 'Екатеринбург', 'Белинского', '5', 1, '40'),
       ('695aecfe-152f-421d-9c4b-5831b812cd22', 45.00, 'Екатеринбург', 'Белинского', '5', 2, '10'),
       ('695aecfe-152f-421d-9c4b-5831b812cd23', 0, '', '', '', 0, '0');


INSERT INTO gages(id, serial_number, flat_id, type_gage, manufacturer, installation_date)
VALUES ('5f8c8fb4-503a-4727-a47a-19d539fa6571', '1234567', '695aecfe-152f-421d-9c4b-5831b812cd21', 'THERMAL_ENERGY_METER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6572', '1234568', '695aecfe-152f-421d-9c4b-5831b812cd21', 'ELECTRICAL_ENERGY_METER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6573', '1234510', '695aecfe-152f-421d-9c4b-5831b812cd21', 'COLD_WATER_METER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6574', '2345671', '695aecfe-152f-421d-9c4b-5831b812cd22', 'THERMAL_ENERGY_METER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6575', '2345681', '695aecfe-152f-421d-9c4b-5831b812cd22', 'ELECTRICAL_ENERGY_METER', 'Энергомир', '2022-06-01'),
       ('5f8c8fb4-503a-4727-a47a-19d539fa6576', '2345101', '695aecfe-152f-421d-9c4b-5831b812cd22', 'COLD_WATER_METER', 'Энергомир', '2022-06-01');

INSERT INTO contract(id, contract_number, signed_date, flat_id)
VALUES ('3cd5a670-7888-11ed-a1eb-0242ac120001', '00500110122022', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd21'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120002', '00500210122022', '2022-12-10', '695aecfe-152f-421d-9c4b-5831b812cd22'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120003', '00000000000000', current_date, '695aecfe-152f-421d-9c4b-5831b812cd23'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120004', '10000000000000', current_date, '695aecfe-152f-421d-9c4b-5831b812cd23'),
       ('3cd5a670-7888-11ed-a1eb-0242ac120005', '20000000000000', current_date, '695aecfe-152f-421d-9c4b-5831b812cd23');

INSERT INTO db_users(id, password, username, contract_id)
VALUES ('6235e344-795f-11ed-a1eb-0242ac120001', '$2a$10$s.Sth7cC4F8z/6boyBJ8hOwtBuyLTfWITLV0l/LlUbAPstTCVfRta', 'admin', '3cd5a670-7888-11ed-a1eb-0242ac120003'),
       ('6235e344-795f-11ed-a1eb-0242ac120002', '$2a$10$s.Sth7cC4F8z/6boyBJ8hOwtBuyLTfWITLV0l/LlUbAPstTCVfRta', 'mod', '3cd5a670-7888-11ed-a1eb-0242ac120004'),
       ('6235e344-795f-11ed-a1eb-0242ac120003', '$2a$10$s.Sth7cC4F8z/6boyBJ8hOwtBuyLTfWITLV0l/LlUbAPstTCVfRta', 'dispatcher', '3cd5a670-7888-11ed-a1eb-0242ac120005');

INSERT INTO user_roles(user_id, role_id)
VALUES ('6235e344-795f-11ed-a1eb-0242ac120001', '798060ff-ed9b-4745-857c-a7a2f4a2e3a3'),
       ('6235e344-795f-11ed-a1eb-0242ac120002', '798060ff-ed9b-4745-857c-a7a2f4a2e3a2'),
       ('6235e344-795f-11ed-a1eb-0242ac120003', '798060ff-ed9b-4745-857c-a7a2f4a2e3a4');

INSERT INTO gages_data(id, gage_id, user_id, data, departure_date)
VALUES ('c6346318-7cc8-11ed-a1eb-0242ac120001', '5f8c8fb4-503a-4727-a47a-19d539fa6574', '23118c66-ba48-4f89-b587-5a623161783b', 'data', '2022-11-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120002', '5f8c8fb4-503a-4727-a47a-19d539fa6575', '23118c66-ba48-4f89-b587-5a623161783b', 'data', '2022-11-23'),
       ('c6346318-7cc8-11ed-a1eb-0242ac120003', '5f8c8fb4-503a-4727-a47a-19d539fa6576', '23118c66-ba48-4f89-b587-5a623161783b', 'data', '2022-11-23');
