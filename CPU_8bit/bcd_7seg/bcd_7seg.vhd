library ieee;
use ieee.std_logic_1164.all;
entity bcd_7seg is
port(B:in std_logic_vector(3 downto 0);
	  H:out std_logic_vector(6 downto 0)
);
end bcd_7seg;

architecture behavioral of bcd_7seg is
begin
	process(B)
	begin 
		case B is
		when "0000"=>H<="1000000";--0
		when "0001"=>H<="1111001";--1
		when "0010"=>H<="0100100";--2
		when "0011"=>H<="0110000";--3
		when "0100"=>H<="0011001";--4
		when "0101"=>H<="0010010";--5
		when "0110"=>H<="0000011";--6
		when "0111"=>H<="1111000";--7
		when "1000"=>H<="0000000";--8
		when "1001"=>H<="0011000";--9
		when "1010"=>H<="0001000";--A
		when "1011"=>H<="0000000";--B
		when "1100"=>H<="1000110";--C
		when "1101"=>H<="1000000";--D
		when "1110"=>H<="0000110";--E
		when "1111"=>H<="0001110";--F
		when others=>H<="1111111";--others
		end case;
	end process;
end behavioral;
