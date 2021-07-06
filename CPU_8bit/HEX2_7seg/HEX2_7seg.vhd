library ieee;
use ieee.std_logic_1164.all;
entity hex2_7seg is
port (
	Q:in std_logic_vector(7 downto 0);
	HEX1,HEX2:out std_logic_vector(6 downto 0)
);
end hex2_7seg;

architecture bhv of hex2_7seg is
signal Q_high,Q_low:std_logic_vector(3 downto 0);
component bcd_7seg
port(B:in std_logic_vector(3 downto 0);
	  H:out std_logic_vector(6 downto 0)
);
end component;
begin
	Q_high<=Q(7 downto 4);
	Q_low<=Q(3 downto 0);
	H1: bcd_7seg port map(B=>Q_High,H=>hex1);
	H0: bcd_7seg port map(B=>Q_low,H=>hex2);
end;