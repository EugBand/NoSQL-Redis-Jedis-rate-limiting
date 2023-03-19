INSERT IGNORE INTO user_rate
SET account_id = 'common_customer',
allowed_requests = 5,
time_interval = 120;
INSERT IGNORE INTO user_rate
SET account_id = 'very_important_customer',
allowed_requests = 15,
time_interval = 60;
INSERT IGNORE INTO ip_rate
SET account_ip = '1.2.3.4',
allowed_requests = 3,
time_interval = 180;
INSERT IGNORE INTO ip_rate
SET account_ip = '123.123.123.123',
allowed_requests = 12,
time_interval = 60;
