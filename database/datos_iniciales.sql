INSERT INTO espacio (codigo, tipo) VALUES
('C-01', 'CARRO'),
('C-02', 'CARRO'),
('C-03', 'CARRO'),
('M-01', 'MOTO'),
('M-02', 'MOTO')
ON CONFLICT (codigo) DO NOTHING;
