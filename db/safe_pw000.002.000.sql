CREATE TABLE identity (
                         user_uuid VARCHAR(36) NOT NULL,
                         username VARCHAR(50) NOT NULL,
                         password_hash VARCHAR(255) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (user_uuid)
);
#pw: pizza1234
INSERT INTO identity (user_uuid, username, password_hash) VALUE ('u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d', 'minh', '6aadfcc5f85db7261a6c772e1c16654a84af87a1f430f576bb61cc4e9be64462');

INSERT INTO account (account_uuid, username, path, email, password_encoded, user_uuid) VALUES
                                                                                           ('a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d', 'alex_g', 'alex-g', 'alex.g@example.com', 'p69u6+8P6NUn0QOEnz7u7g==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('b2c3d4e5-f67a-8b9c-0d1e-2f3a4b5c6d7e', 'bella_b', 'bella-b', 'bella.b@example.com', 'L6uUeS7j8HjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('c3d4e5f6-7a8b-9c0d-1e2f-3a4b5c6d7e8f', 'charlie_k', 'charlie-k', 'charlie.k@example.com', '2D43yC9B8R8xWwWkW9bX2w==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('d4e5f67a-8b9c-0d1e-2f3a-4b5c6d7e8f9a', 'daniela_m', 'daniela-m', 'daniela.m@example.com', 'm0fPZ2f2kK8ZJscR1X8mZQ==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('e5f67a8b-9c0d-1e2f-3a4b-5c6d7e8f9a0b', 'ethan_w', 'ethan-w', 'ethan.w@example.com', 'X8mWwS8j5HjHscwR1p2SXA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('f67a8b9c-0d1e-2f3a-4b5c-6d7e8f9a0b1c', 'fiona_r', 'fiona-r', 'fiona.r@example.com', 'q99uZ2v2kHjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('7a8b9c0d-1e2f-3a4b-5c6d-7e8f9a0b1c2d', 'gavin_t', 'gavin-t', 'gavin.t@example.com', 'p69u6+8P7NUn0QOEnz7u7g==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('8b9c0d1e-2f3a-4b5c-6d7e-8f9a0b1c2d3e', 'hanna_s', 'hanna-s', 'hanna.s@example.com', 'L6uUeS7j9HjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('9c0d1e2f-3a4b-5c6d-7e8f-9a0b1c2d3e4f', 'ian_p', 'ian-p', 'ian.p@example.com', '2D43yC9B5R8xWwWkW9bX2w==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('0d1e2f3a-4b5c-6d7e-8f9a-0b1c2d3e4f5a', 'julia_v', 'julia-v', 'julia.v@example.com', 'm0fPZ2f2lK8ZJscR1X8mZQ==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('1e2f3a4b-5c6d-7e8f-9a0b-1c2d3e4f5a6b', 'kevin_d', 'kevin-d', 'kevin.d@example.com', 'X8mWwS8j6HjHscwR1p2SXA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('2f3a4b5c-6d7e-8f9a-0b1c-2d3e4f5a6b7c', 'lara_f', 'lara-f', 'lara.f@example.com', 'q99uZ2v2lHjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('3a4b5c6d-7e8f-9a0b-1c2d-3e4f5a6b7c8d', 'marcus_l', 'marcus-l', 'marcus.l@example.com', 'p69u6+8P5NUn0QOEnz7u7g==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('4b5c6d7e-8f9a-0b1c-2d3e-4f5a6b7c8d9e', 'nina_h', 'nina-h', 'nina.h@example.com', 'L6uUeS7j4HjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('5c6d7e8f-9a0b-1c2d-3e4f-5a6b7c8d9e0f', 'oliver_j', 'oliver-j', 'oliver.j@example.com', '2D43yC9B6R8xWwWkW9bX2w==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('6d7e8f9a-0b1c-2d3e-4f5a-6b7c8d9e0f1a', 'paula_c', 'paula-c', 'paula.c@example.com', 'm0fPZ2f2mK8ZJscR1X8mZQ==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('7e8f9a0b-1c2d-3e4f-5a6b-7c8d9e0f1a2b', 'quinn_e', 'quinn-e', 'quinn.e@example.com', 'X8mWwS8j7HjHscwR1p2SXA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('8f9a0b1c-2d3e-4f5a-6b7c-8d9e0f1a2b3c', 'rachel_y', 'rachel-y', 'rachel.y@example.com', 'q99uZ2v2mHjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('9a0b1c2d-3e4f-5a6b-7c8d-9e0f1a2b3c4d', 'samuel_x', 'samuel-x', 'samuel.x@example.com', 'p69u6+8P4NUn0QOEnz7u7g==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('0b1c2d3e-4f5a-6b7c-8d9e-0f1a2b3c4d5e', 'tanya_q', 'tanya-q', 'tanya.q@example.com', 'L6uUeS7j5HjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('1c2d3e4f-5a6b-7c8d-9e0f-1a2b3c4d5e6f', 'umar_u', 'umar-u', 'umar.u@example.com', '2D43yC9B7R8xWwWkW9bX2w==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('2d3e4f5a-6b7c-8d9e-0f1a-2b3c4d5e6f7a', 'valerie_o', 'valerie-o', 'valerie.o@example.com', 'm0fPZ2f2nK8ZJscR1X8mZQ==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('3e4f5a6b-7c8d-9e0f-1a2b-3c4d5e6f7a8b', 'william_n', 'william-n', 'william.n@example.com', 'X8mWwS8j4HjHscwR1p2SXA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('4f5a6b7c-8d9e-0f1a-2b3c-4d5e6f7a8b9c', 'xenia_i', 'xenia-i', 'xenia.i@example.com', 'q99uZ2v2nHjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('5a6b7c8d-9e0f-1a2b-3c4d-5e6f7a8b9c0d', 'yousef_l', 'yousef-l', 'yousef.l@example.com', 'p69u6+8P1NUn0QOEnz7u7g==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('6b7c8d9e-0f1a-2b3c-4d5e-6f7a8b9c0d1e', 'zoe_p', 'zoe-p', 'zoe.p@example.com', 'L6uUeS7j1HjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('7c8d9e0f-1a2b-3c4d-5e6f-7a8b9c0d1e2f', 'aaron_z', 'aaron-z', 'aaron.z@example.com', '2D43yC9B4R8xWwWkW9bX2w==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('8d9e0f1a-2b3c-4d5e-6f7a-8b9c0d1e2f3a', 'brooke_x', 'brooke-x', 'brooke.x@example.com', 'm0fPZ2f2oK8ZJscR1X8mZQ==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('9e0f1a2b-3c4d-5e6f-7a8b-9c0d1e2f3a4b', 'connor_v', 'connor-v', 'connor.v@example.com', 'X8mWwS8j1HjHscwR1p2SXA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d'),
                                                                                           ('0f1a2b3c-4d5e-6f7a-8b9c-0d1e2f3a4b5c', 'daisy_w', 'daisy-w', 'daisy.w@example.com', 'q99uZ2v2oHjHscwB1p2SWA==', 'u1v2w3x4-y5z6-7a8b-9c0d-1e2f3a4b5c6d');


insert version (major, minor, patch) values (0, 2, 0);
commit;