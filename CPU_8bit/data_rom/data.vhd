library ieee;
use ieee.std_logic_1164.all;
entity data is
port(S:in std_logic_vector(1 downto 0);
	data:out std_logic_vector(7 downto 0)
);
end data;
architecture bhv of data is
begin
process(S)
begin
		case S is
			when "00"=>data<="01010101";--55
			when "01"=>data<="00000100";--4
			when "10"=>data<="10101010";--AA
			when "11"=>data<="00000001";--1
		end case;
end process;
end bhv;